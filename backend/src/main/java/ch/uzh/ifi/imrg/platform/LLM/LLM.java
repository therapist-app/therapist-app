// src/main/java/ch/uzh/ifi/imrg/platform/LLM/LLM.java

package ch.uzh.ifi.imrg.platform.LLM;

import ch.uzh.ifi.imrg.platform.enums.Language;
import ch.uzh.ifi.imrg.platform.rest.dto.input.ChatMessageDTO;
import java.util.List;

public abstract class LLM {

  static <T> T callLLMForObject(
      List<ChatMessageDTO> messages, Class<T> responseType, Language language) {

    return null;
  }

  static String callLLM(List<ChatMessageDTO> messages, Language language) {
    return null;
  }
}
