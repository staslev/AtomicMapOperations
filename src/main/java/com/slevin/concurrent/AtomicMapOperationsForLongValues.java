package com.slevin.concurrent;

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

  public <K> void increase(final ConcurrentMap<K, Long> map, final K key) {
    SomeConcurrentMap.withImmutableValues().putOrTransform(map, key, increaseByOneTransformer);
  }

  public <K> void increase(final ConcurrentMap<K, Long> map, final K key, final long increaseBy) {
    final Function<Long, Long> increaseTransformer = new Function<Long, Long>() {
      @Override
      public Long apply(@Nullable final Long previousValue) {
        return previousValue == null ? increaseBy : previousValue + increaseBy;
      }
    };

    SomeConcurrentMap.withImmutableValues().putOrTransform(map, key, increaseTransformer);
  }

  public <K> void decrease(final ConcurrentMap<K, Long> map, final K key) {
    SomeConcurrentMap.withImmutableValues().putOrTransform(map, key, decreaseByOneTransformer);
  }

  public <K> void decrease(final ConcurrentMap<K, Long> map, final K key, final long decreaseBy) {
    final Function<Long, Long> decreaseTransformer = new Function<Long, Long>() {
      @Override
      public Long apply(@Nullable final Long previousValue) {
        return previousValue == null ? -1 * decreaseBy : previousValue - decreaseBy;
      }
    };

    SomeConcurrentMap.withImmutableValues().putOrTransform(map, key, decreaseTransformer);
  }
}
