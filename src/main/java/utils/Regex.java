package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

  public static String getMatch(String regex, String toMatch) {
    final Pattern pattern = Pattern.compile(regex);
    final Matcher matcher = pattern.matcher(toMatch);
    String match = null;
    if (matcher.matches())
      match = matcher.group(1);
    return match;
  }

}
