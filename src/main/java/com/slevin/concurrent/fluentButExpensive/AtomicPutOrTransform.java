package com.slevin.concurrent.fluentButExpensive;

import com.google.common.base.Function;
import com.slevin.concurrent.SomeConcurrentMap;

import java.util.concurrent.ConcurrentMap;

public class AtomicPutOrTransform<K, V> {

  private final ConcurrentMap<K, V> map;
  private final K key;
  private final Function<V, V> valueTransformer;

  AtomicPutOrTransform(final ConcurrentMap<K, V> map, final K key, final Function<V, V> valueTransformer) {
    this.map = map;
    this.key = key;
    this.valueTransformer = valueTransformer;
  }

  public void atomically() {
    SomeConcurrentMap.withImmutableValues().putOrTransform(map, key, valueTransformer);
  }
}
