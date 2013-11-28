package com.github.staslev.concurrent.nonblocking;

import com.google.common.base.Function;

import javax.annotation.Nullable;
import java.util.concurrent.ConcurrentMap;

public class AtomicMapOperationsForLongValues {

  final Function<Long, Long> increaseByOneTransformer = new Function<Long, Long>() {
    @Override
    public Long apply(@Nullable final Long previousValue) {
      return previousValue == null ? 1L : previousValue + 1;
    }
  };

  final Function<Long, Long> decreaseByOneTransformer = new Function<Long, Long>() {
    @Override
    public Long apply(@Nullable final Long previousValue) {
      return previousValue == null ? -1L : previousValue - 1;
    }
  };

  AtomicMapOperationsForLongValues() {
  }

  /**
   * Atomically increases the value assigned to <code>key</code> by 1. If <code>key</code> is not present in the map, its value is set to 1.
   * 
   * @param map The map on each to perform the operation on.
   * @param key The key whose value is to be put or transformed.
   * @param <K> The type of the keys.
   */
  public <K> void increase(final ConcurrentMap<K, Long> map, final K key) {
    NonBlockingOperations.forMap.withImmutableValues().putOrTransform(map, key, increaseByOneTransformer);
  }

  /**
   * Atomically increases the value assigned to <code>key</code> by <code>increaseBy</code>. If <code>key</code> is not present in the map, its value is set to <code>increaseBy</code>.
   *
   * @param map The map on each to perform the operation on.
   * @param key The key whose value is to be put or transformed.
   * @param <K> The type of the keys.
   */
  public <K> void increase(final ConcurrentMap<K, Long> map, final K key, final long increaseBy) {
    final Function<Long, Long> increaseTransformer = new Function<Long, Long>() {
      @Override
      public Long apply(@Nullable final Long previousValue) {
        return previousValue == null ? increaseBy : previousValue + increaseBy;
      }
    };

    NonBlockingOperations.forMap.withImmutableValues().putOrTransform(map, key, increaseTransformer);
  }

  /**
   * Atomically decreases the value of <code>key</code> by 1, if the given key is not present in the map, its value is set to -1.
   *
   * @param map The map on each to perform the operation on.
   * @param key The key whose value is to be put or transformed.
   * @param <K> The type of the keys.
   */
  public <K> void decrease(final ConcurrentMap<K, Long> map, final K key) {
    NonBlockingOperations.forMap.withImmutableValues().putOrTransform(map, key, decreaseByOneTransformer);
  }

  /**
   * Atomically decreases the value of <code>key</code> by <code>decreaseBy</code>, if <code>key</code> is not present in the map, its value is set to <code>(-1 * decreaseBy)</code>.
   *
   * @param map The map on each to perform the operation on.
   * @param key The key whose value is to be put or transformed.
   * @param <K> The type of the keys.
   */
  public <K> void decrease(final ConcurrentMap<K, Long> map, final K key, final long decreaseBy) {
    final Function<Long, Long> decreaseTransformer = new Function<Long, Long>() {
      @Override
      public Long apply(@Nullable final Long previousValue) {
        return previousValue == null ? -1 * decreaseBy : previousValue - decreaseBy;
      }
    };

    NonBlockingOperations.forMap.withImmutableValues().putOrTransform(map, key, decreaseTransformer);
  }
}
