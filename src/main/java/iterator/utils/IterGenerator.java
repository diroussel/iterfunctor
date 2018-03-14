package iterator.utils;

import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

import static java.util.Collections.emptyListIterator;

public class IterGenerator {


  public static <X> IterFunctor<X> generate(final Supplier<List<X>> supplier) {
    return IterFunctor.lift(() -> new Iterator<X>() {
      Iterator<X> listIter = emptyListIterator();

      @Override
      public boolean hasNext() {
        if (listIter.hasNext()) {
          return true;
        }
        final List<X> list = supplier.get();
        if (list.size() > 0) {
          listIter = list.iterator();
          return true;
        } else {
          return false;
        }
      }

      @Override
      public X next() {
        return listIter.next();
      }
    });

  }
}
