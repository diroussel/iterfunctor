package iterator.utils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.util.Collections.emptyListIterator;
import static java.util.Collections.singletonList;

public class IterFunctor<T> implements Iterable<T> {

  private final Iterable<T> iter;

  public static <X> IterFunctor<X> lift(final Iterable<X> iter) {
    return new IterFunctor<>(iter);
  }

  public static <X> IterFunctor<X> lift(final X value) {
    return new IterFunctor<>(singletonList(value));
  }

  public static <X> IterFunctor<X> lift(final X... values) {
    return new IterFunctor<>(Arrays.asList(values));
  }

  private IterFunctor(final Iterable<T> iterable) {
    this.iter = iterable;
  }

  public <R> IterFunctor<R> map(final Function<T, R> func) {
    return lift(() -> {
      Iterator<T> i = iter.iterator();

      return new Iterator<R>() {
        public boolean hasNext() {
          return i.hasNext();
        }

        public R next() {
          return func.apply(i.next());
        }
      };
    });
  }


  public <R> IterFunctor<R> flatMap(final Function<T, Iterable<R>> func) {
    return lift(() -> {
      final Iterator<Iterable<R>> outerIter = map(func).iterator();

      return new Iterator<R>() {
        Iterator<R> innerIter = emptyListIterator();

        @Override
        public boolean hasNext() {
          if (innerIter.hasNext()) {
            return true;
          }
          if (!outerIter.hasNext()) {
            return false;
          }
          innerIter = outerIter.next().iterator();
          return hasNext();
        }

        @Override
        public R next() {
          return innerIter.next();
        }
      };
    });
  }

  @Override
  public Iterator<T> iterator() {
    return iter.iterator();
  }

  public Optional<T> reduce(BiFunction<T, T, T> reducer) {
    final Iterator<T> iterator = iterator();

    if (!iterator.hasNext()) {
      return Optional.empty();
    }

    T accumulator = iterator.next();
    while (iterator.hasNext()) {
      accumulator = reducer.apply(accumulator, iterator.next());
    }
    return Optional.of(accumulator);
  }

  /**
   * Use any collector from JDK Stream package to collect elements
   */
  public <R, A> R collect(Collector<? super T, A, R> collector) {
    final Supplier<A> supplierOfEmpty = collector.supplier();
    final A accumulator = supplierOfEmpty.get();
    final BiConsumer<A, ? super T> add = collector.accumulator();
    iterator().forEachRemaining(val -> add.accept(accumulator, val));
    return collector.finisher().apply(collector.combiner().apply(accumulator, supplierOfEmpty.get()));
  }
}
