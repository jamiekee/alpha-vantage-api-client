package io.jamiekee.alphavantage.api.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

  /**
   * Simple method to extract a single variable from a string given a regex
   * statement.
   * @param regex The regex string to obtain the variable.
   * @param toMatch The string which includes the variable you want extracted.
   * @return The variable from the string.
   */
  public static String getMatch(String regex, String toMatch) {
    final Pattern pattern = Pattern.compile(regex);
    final Matcher matcher = pattern.matcher(toMatch);
    String match = null;
    if (matcher.matches())
      match = matcher.group(1);
    return match;
  }

}
