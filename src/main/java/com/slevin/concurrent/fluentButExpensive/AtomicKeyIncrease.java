package com.slevin.concurrent.fluentButExpensive;

import com.google.common.base.Function;

import javax.annotation.Nullable;
import java.util.concurrent.ConcurrentMap;

public class AtomicKeyIncrease<K> {

  private final ConcurrentMap<K, Long> map;
  private final K key;

  AtomicKeyIncrease(final ConcurrentMap<K, Long> map, final K key) {
    this.map = map;
    this.key = key;
  }

  public AtomicPutOrTransform<K, Long> increase() {
    return increase(1L);
  }

  public AtomicPutOrTransform<K, Long> increase(final long value) {

    final Function<Long, Long> increaseBy = new Function<Long, Long>() {
      @Override
      public Long apply(@Nullable final Long input) {
        return input + value;
      }
    };

    return new AtomicPutOrTransform<K, Long>(map, key, increaseBy);
  }

  public AtomicPutOrTransform<K, Long> decrease() {
    return decrease(1L);
  }

  public AtomicPutOrTransform<K, Long> decrease(final long value) {

    final Function<Long, Long> decreaseBy = new Function<Long, Long>() {
      @Override
      public Long apply(@Nullable final Long input) {
        return input - value;
      }
    };

    return new AtomicPutOrTransform<K, Long>(map, key, decreaseBy);
  }
}
