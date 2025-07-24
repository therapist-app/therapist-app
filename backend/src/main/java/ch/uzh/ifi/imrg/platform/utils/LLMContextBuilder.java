package ch.uzh.ifi.imrg.platform.utils;

import ch.uzh.ifi.imrg.platform.entity.HasLLMContext;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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

  public static StringBuilder getOwnProperties(Object object, Integer level) {
    return new StringBuilder(FormatUtil.indentBlock(LLMContextBuilder.build(object), level, false));
  }

  public static void addLLMContextOfListOfEntities(
      StringBuilder sb, List<? extends HasLLMContext> entities, String label, Integer level) {

    if (!entities.isEmpty()) {
      sb.append(FormatUtil.indentBlock("\n--- " + label + "s ---\n", level, false));

      Boolean first = true;
      for (HasLLMContext entity : entities) {
        if (first) {
          first = false;
        } else {
          sb.append(FormatUtil.indentBlock("\n------------------------------------", level, true));
        }

        sb.append(FormatUtil.indentBlock("\n" + label + ": " + entity.getId(), level, true));

        sb.append(entity.toLLMContext(level + 1));
      }
    }
  }
}
