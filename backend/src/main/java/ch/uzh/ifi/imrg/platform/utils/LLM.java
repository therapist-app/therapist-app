package ch.uzh.ifi.imrg.platform.utils;

import ch.uzh.ifi.imrg.platform.rest.dto.input.ChatMessageDTO;
import java.util.List;

public interface LLM {

  static String callLLM(List<ChatMessageDTO> messages) {
    return null;
  }
}
