package ch.uzh.ifi.imrg.platform.entity;

public interface HasLLMContext {
  String getId();

  String toLLMContext(Integer level);
}
