package iterator.utils;

import org.junit.Test;

import static iterator.utils.TestUtils.join;
import static iterator.utils.TestUtils.lists;
import static org.junit.Assert.assertEquals;

public class TestUtilsTest {

  @Test
  public void testlistOfLength() {
    assertEquals("", join(lists(0)));
    assertEquals("a", join(lists(1)));
    assertEquals("abb", join(lists(2)));
    assertEquals("abbccc", join(lists(3)));
  }
}
