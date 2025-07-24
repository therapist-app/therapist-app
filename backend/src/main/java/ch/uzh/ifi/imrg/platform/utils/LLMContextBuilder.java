package ch.uzh.ifi.imrg.platform.utils;

import java.util.Arrays;
import java.util.Comparator;

public class LLMContextBuilder {
  public static String build(Object object) {
    StringBuilder sb = new StringBuilder();
    Arrays.stream(object.getClass().getDeclaredFields())
        .filter(field -> field.isAnnotationPresent(LLMContextField.class))
        .sorted(Comparator.comparingInt(f -> f.getAnnotation(LLMContextField.class).order()))
        .forEach(
            field -> {
              try {
                field.setAccessible(true);
                LLMContextField annotation = field.getAnnotation(LLMContextField.class);
                Object value = field.get(object);
                FormatUtil.appendDetail(sb, annotation.label(), value);
              } catch (IllegalAccessException e) {
                e.printStackTrace();
              }
            });
    return sb.toString();
  }
}
