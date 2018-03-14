package iterator.utils;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static iterator.utils.IterFunctor.lift;
import static iterator.utils.TestUtils.join;
import static iterator.utils.TestUtils.lists;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class IterFunctorTest {

  @Test
  public void testFlatMap() {
    List<Integer> ints = asList(2, 3);

    final IterFunctor<Integer> ifunc = lift(ints);

    final Iterable<String> iterable = ifunc
      .flatMap(i -> lists(i));

    final String actual = join(iterable);
    assertEquals("abbabbccc", actual);
  }

  @Test
  public void testMap() {
    List<Integer> ints = asList(2, 3);

    final Iterable<String> iterable = lift(ints)
      .map(i -> lists(i))
      .map(list -> list.size())
      .map(length -> Integer.toString(length));

    final String actual = join(iterable, ", ");
    assertEquals("2, 3", actual);
  }

  @Test
  public void testReduce() {

    assertEquals("Hello World!",
      lift("Hello", " ", "World", "!")
        .reduce(String::concat)
        .get());
  }

  @Test
  public void testReduceEmpty() {
    List<String> ints = asList();

    assertEquals(false,
      lift(ints)
        .reduce(String::concat)
        .isPresent());
  }

  @Test
  public void testReduceSingle() {
    List<String> ints = asList();

    assertEquals(new Integer(99),
      lift(99)
        .reduce((a,b) -> a + b)
        .get());
  }


  @Test
  public void testMapRestart() {
    List<Integer> ints = asList(2, 3, 5);

    final Iterable<String> iterable = lift(ints)
      .map(i -> Integer.toString(i));

    final String result1 = join(iterable, ", ");
    final String result2 = join(iterable, ", ");
    assertEquals("2, 3, 5", result1);
    assertEquals(result1, result2);
  }

  @Test
  public void testCollect() {
    List<Integer> ints = asList(2, 3, 5);

    final List<String> iterable = lift(ints)
      .map(i -> Integer.toString(i))
      .collect(Collectors.toList());

    assertEquals("[2, 3, 5]", iterable.toString());
  }
}
