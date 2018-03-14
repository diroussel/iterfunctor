package iterator.utils;

import java.util.ArrayList;
import java.util.List;

public class TestUtils {


  public static String join(final Iterable<String> iterable) {
    StringBuilder bob = new StringBuilder();
    for (String s : iterable) {
      bob.append(s);
    }

    return bob.toString();
  }

  public static String join(final Iterable<String> iterable, String separator) {
    StringBuilder bob = new StringBuilder();
    boolean notFirst = false;
    for (String s : iterable) {
      if (notFirst) {
        bob.append(separator);
      }
      notFirst = true;
      bob.append(s);
    }

    return bob.toString();
  }


  public static String repeat(char ch, int times) {
    final StringBuilder bob = new StringBuilder(times);
    for (int i = 0; i < times; i++) {
      bob.append(ch);
    }
    return bob.toString();
  }

  public static List<String> lists(int length) {
    ArrayList<String> list = new ArrayList<>(length);
    for (int i = 1; i <= length; i++) {
      list.add(repeat((char) ('a' + i - 1), i));
    }
    return list;
  }
}
