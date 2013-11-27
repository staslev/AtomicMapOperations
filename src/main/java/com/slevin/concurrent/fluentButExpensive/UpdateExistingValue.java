package com.slevin.concurrent.fluentButExpensive;

import com.google.common.base.Function;

import java.util.concurrent.ConcurrentMap;

public class UpdateExistingValue<K, V> {

  private ConcurrentMap<K, V> map;
  private K key;

  UpdateExistingValue(final ConcurrentMap<K, V> map, final K key) {
    this.map = map;
    this.key = key;
  }

  public AtomicTransformExisting<K, V> byApplying(Function<V, V> valueTransformer) {
    return new AtomicTransformExisting<K, V>(map, key, valueTransformer);
  }
}
