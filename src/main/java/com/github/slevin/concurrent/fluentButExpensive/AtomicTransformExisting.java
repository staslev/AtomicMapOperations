package com.github.slevin.concurrent.fluentButExpensive;

import com.google.common.base.Function;
import com.outbrain.storm.infra.utils.ConcurrentMapOperations.SomeConcurrentMap;

import java.util.concurrent.ConcurrentMap;

public class AtomicTransformExisting<K, V> {

  private ConcurrentMap<K, V> map;
  private K key;
  private Function<V, V> valueTransformer;

  AtomicTransformExisting(final ConcurrentMap<K, V> map, final K key, final Function<V, V> valueTransformer) {
    this.map = map;
    this.key = key;
    this.valueTransformer = valueTransformer;
  }

  public boolean atomically() {
    return SomeConcurrentMap.withImmutableValues().transformIfPresent(map, key, valueTransformer);
  }
}
