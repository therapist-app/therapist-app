package ch.uzh.ifi.imrg.platform.utils;

import ch.uzh.ifi.imrg.platform.LLM.LLM;
import ch.uzh.ifi.imrg.platform.LLM.LLMFactory;
import ch.uzh.ifi.imrg.platform.entity.Therapist;
import ch.uzh.ifi.imrg.platform.enums.LLMModel;
import ch.uzh.ifi.imrg.platform.repository.TherapistRepository;
import ch.uzh.ifi.imrg.platform.rest.dto.input.ChatMessageDTO;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpServerErrorException;

public final class DocumentSummarizerUtil {

  public static final int MAX_SUMMARY_CHARS = 10_000;

  private static final int MAX_LLM_CALLS = 3;

  private static final int SINGLE_CALL_CHAR_BUDGET = MAX_SUMMARY_CHARS / MAX_LLM_CALLS;

  private static final Logger log = LoggerFactory.getLogger(DocumentSummarizerUtil.class);

  private static final int RETRIES = 3;
  private static final Duration BACK_OFF = Duration.ofSeconds(2);

  private static final Semaphore LLM_SEMAPHORE = new Semaphore(1, true);

  private DocumentSummarizerUtil() {}

  private static List<String> buildChunks(String fullText, int modelCharCap, int maxCalls) {

    String[] pages = fullText.split("\f", -1);
    boolean hasPageMarkers = pages.length > 1;

    List<String> chunks = new ArrayList<>(maxCalls);

    if (hasPageMarkers) {
      StringBuilder curr = new StringBuilder();

      for (String p : pages) {
        if (curr.length() + p.length() > modelCharCap && chunks.size() < maxCalls - 1) {
          chunks.add(curr.toString());
          curr = new StringBuilder();
        }
        curr.append(p);
      }
      chunks.add(curr.toString());
    }

    if (!hasPageMarkers || chunks.size() == 1) {
      chunks.clear();
      int slice = Math.min(modelCharCap, (int) Math.ceil((double) fullText.length() / maxCalls));

      for (int off = 0; off < fullText.length() && chunks.size() < maxCalls; off += slice) {
        chunks.add(fullText.substring(off, Math.min(off + slice, fullText.length())));
      }
    }

    return chunks;
  }

  public static String summarise(String fullText, Therapist therapist, TherapistRepository repo) {

    LLMModel model = repo.findById(therapist.getId()).map(Therapist::getLlmModel).orElse(null);
    if (model == null) {
      model = LLMModel.LOCAL_UZH;
    }

    LLM llm = LLMFactory.getInstance(model);
    int modelCharCap = charBudgetFor(model);
    int perChunkLimit = SINGLE_CALL_CHAR_BUDGET - 50;

    List<String> chunks =
        fullText.length() <= modelCharCap
            ? List.of(fullText)
            : buildChunks(fullText, modelCharCap, MAX_LLM_CALLS);

    List<String> partials = new ArrayList<>(chunks.size());
    for (String c : chunks) {
      partials.add(generateSummarySerialised(c, llm, perChunkLimit, modelCharCap));
    }

    String combined = String.join(System.lineSeparator().repeat(2), partials);
    return combined.length() > MAX_SUMMARY_CHARS
        ? combined.substring(0, MAX_SUMMARY_CHARS)
        : combined;
  }

  private static String generateSummarySerialised(
      String text, LLM llm, int charLimit, int modelCharCap) {

    if (text.length() > modelCharCap) {
      text = text.substring(0, modelCharCap);
    }

    acquireLock();
    try {
      return callWithRetry(text, llm, charLimit);
    } finally {
      LLM_SEMAPHORE.release();
    }
  }

  private static int charBudgetFor(LLMModel model) {
    if (model == LLMModel.AZURE_OPENAI) {
      return 150_000;
    }
    final double SAFETY = 0.85;
    final int CHARS_PER_TOKEN = 4;
    long budget = Math.round(model.getMaxTokens() * CHARS_PER_TOKEN * SAFETY);
    return (int) Math.min(budget, Integer.MAX_VALUE);
  }

  private static String callWithRetry(String text, LLM llm, int charLimit) {
    RuntimeException last = null;

    for (int attempt = 1; attempt <= RETRIES; attempt++) {
      try {
        String summary = singleCall(text, llm, charLimit);
        return summary.length() > charLimit ? summary.substring(0, charLimit) : summary;
      } catch (HttpServerErrorException ex) {
        last = ex;
        log.warn("LLM call failed with {} – retry {}/{}", ex.getStatusCode(), attempt, RETRIES);
        sleep(BACK_OFF.multipliedBy(attempt));
      }
    }
    throw last;
  }

  private static String singleCall(String text, LLM llm, int charLimit) {
    List<ChatMessageDTO> msgs = new ArrayList<>();
    msgs.add(
        new ChatMessageDTO(
            ChatRole.SYSTEM,
            "You are a helpful assistant that summarises therapist-client documents. "
                + "Return ≤"
                + charLimit
                + " characters, keep language, keep clinically relevant info."));
    msgs.add(new ChatMessageDTO(ChatRole.USER, text));
    return llm.callLLM(msgs, null);
  }

  private static void acquireLock() {
    try {
      LLM_SEMAPHORE.acquire();
    } catch (InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new RuntimeException("Interrupted while waiting for LLM lock", ie);
    }
  }

  private static void sleep(Duration d) {
    try {
      Thread.sleep(d.toMillis());
    } catch (InterruptedException ie) {
      Thread.currentThread().interrupt();
    }
  }
}
