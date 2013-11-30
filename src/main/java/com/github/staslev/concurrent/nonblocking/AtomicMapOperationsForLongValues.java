package com.github.staslev.concurrent.nonblocking;

import java.util.concurrent.ConcurrentMap;

/**
 * 
 * Provides atomic non blocking operations on maps with Long values, this is typically the case when values are 
 * counters of some sort.
 * 
 */
public class AtomicMapOperationsForLongValues {

  AtomicMapOperationsForLongValues() {
  }

  NonBlockingOperations.Aggregator<Long, Long> increaseByAggregator = new NonBlockingOperations.Aggregator<Long, Long>() {
    @Override
    public Long aggregate(final Long input, final Long previousValue) {
      return previousValue + input;
    }
  };

  NonBlockingOperations.Aggregator<Long, Long> decreaseByAggregator = new NonBlockingOperations.Aggregator<Long, Long>() {
    @Override
    public Long aggregate(final Long input, final Long previousValue) {
      return previousValue - input;
    }
  };

  final NonBlockingOperations.Transformer<Long> increaseByOneTransformer = new NonBlockingOperations.Transformer<Long>() {
    @Override
    public Long transform(final Long previousValue) {
      return previousValue == null ? 1L : previousValue + 1;
    }
  };

  final NonBlockingOperations.Transformer<Long> decreaseByOneTransformer = new NonBlockingOperations.Transformer<Long>() {
    @Override
    public Long transform(final Long previousValue) {
      return previousValue == null ? -1L : previousValue - 1;
    }
  };

  /**
   * Atomically increases the value assigned to <code>key</code> by 1. If <code>key</code> is not present in the map, its value is set to 1.
   * 
   * @param map The map to perform the operation on.
   * @param key The key whose value is to be put or transformed.
   * @param <K> The type of the keys.
   */
  public <K> void increase(final ConcurrentMap<K, Long> map, final K key) {
    NonBlockingOperations.forMap.withImmutableValues().putOrTransform(map, key, increaseByOneTransformer);
  }

  /**
   * Atomically increases the value assigned to <code>key</code> by <code>increaseBy</code>. If <code>key</code> is not present in the map, its value is set to <code>increaseBy</code>.
   *
   * @param map The map to perform the operation on.
   * @param key The key whose value is to be put or transformed.
   * @param <K> The type of the keys.
   */
  public <K> void increase(final ConcurrentMap<K, Long> map, final K key, final long increaseBy) {
    NonBlockingOperations.forMap.withImmutableValues().putOrAggregate(map, key, increaseByAggregator, increaseBy);
  }

  /**
   * Atomically decreases the value of <code>key</code> by 1, if the given key is not present in the map, its value is set to -1.
   *
   * @param map The map to perform the operation on.
   * @param key The key whose value is to be put or transformed.
   * @param <K> The type of the keys.
   */
  public <K> void decrease(final ConcurrentMap<K, Long> map, final K key) {
    NonBlockingOperations.forMap.withImmutableValues().putOrTransform(map, key, decreaseByOneTransformer);
  }

  /**
   * Atomically decreases the value of <code>key</code> by <code>decreaseBy</code>, if <code>key</code> is not present in the map, its value is set to <code>(-1 * decreaseBy)</code>.
   *
   * @param map The map to perform the operation on.
   * @param key The key whose value is to be put or transformed.
   * @param <K> The type of the keys.
   */
  public <K> void decrease(final ConcurrentMap<K, Long> map, final K key, final long decreaseBy) {
    NonBlockingOperations.forMap.withImmutableValues().putOrAggregate(map, key, decreaseByAggregator, decreaseBy);
  }
}
