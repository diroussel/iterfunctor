package iterator.utils;

import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static iterator.utils.IterFunctor.lift;
import static iterator.utils.IterGenerator.generate;
import static iterator.utils.TestUtils.lists;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IterGeneratorTest {

  @Test
  public void testGenerate() {

    final Iterator<List<String>> lists = lift(1, 1, 1, 0, 3)
      .map(x -> lists(x))
      .iterator();

    final Optional<String> actual =
      generate(() -> lists.next())
        .map(list -> list.toString())
        .reduce(String::concat);

    assertTrue(actual.isPresent());
    assertEquals("aaa", actual.get());
  }
}
