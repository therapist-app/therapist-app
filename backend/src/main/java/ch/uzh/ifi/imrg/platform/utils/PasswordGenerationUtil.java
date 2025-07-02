package ch.uzh.ifi.imrg.platform.utils;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

public class PasswordGenerationUtil {

  private static final List<String> ADJECTIVES =
      Arrays.asList(
          "Bright", "Calm", "Eager", "Gentle", "Happy", "Jolly", "Kind", "Lively", "Merry", "Nice",
          "Proud", "Silly", "Witty", "Zesty", "Sunny", "Cloudy", "Windy", "Quiet", "Brave",
          "Clever");

  private static final List<String> NOUNS =
      Arrays.asList(
          "River",
          "Mountain",
          "Forest",
          "Ocean",
          "Planet",
          "Star",
          "Comet",
          "Moon",
          "Sun",
          "Sky",
          "Apple",
          "Banana",
          "Cherry",
          "Grape",
          "Lemon",
          "Orange",
          "Peach",
          "Pear",
          "Robot",
          "Dragon");

  private static final SecureRandom RANDOM = new SecureRandom();

  public static String generateUserFriendlyPassword() {

    String adjective = ADJECTIVES.get(RANDOM.nextInt(ADJECTIVES.size()));

    String noun = NOUNS.get(RANDOM.nextInt(NOUNS.size())).toLowerCase();

    int number = 10 + RANDOM.nextInt(90);

    String password = adjective + noun + number;

    if (password.length() < 8) {
      return generateUserFriendlyPassword();
    }

    return password;
  }

  public static String generateAccessKey() {
    byte[] bytes = new byte[16]; // 128-bit access key
    RANDOM.nextBytes(bytes);
    StringBuilder sb = new StringBuilder();
    for (byte b : bytes) {
      sb.append(String.format("%02x", b));
    }
    return sb.toString();
  }
}
