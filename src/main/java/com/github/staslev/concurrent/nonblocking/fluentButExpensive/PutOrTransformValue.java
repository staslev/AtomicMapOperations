package com.github.staslev.concurrent.nonblocking.fluentButExpensive;

import com.google.common.base.Function;

import java.util.concurrent.ConcurrentMap;

public class PutOrTransformValue<K, V> {

  private final ConcurrentMap<K, V> map;
  private final K key;

  PutOrTransformValue(final ConcurrentMap<K, V> map, final K key) {
    this.map = map;
    this.key = key;
  }

  public AtomicPutOrTransform<K, V> byApplying(final Function<V, V> valueTransformer) {
    return new AtomicPutOrTransform<K, V>(map, key, valueTransformer);
  }
}
