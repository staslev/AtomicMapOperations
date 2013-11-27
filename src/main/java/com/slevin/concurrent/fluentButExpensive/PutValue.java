package com.slevin.concurrent.fluentButExpensive;

import java.util.concurrent.ConcurrentMap;

public class PutValue<K, V> {
  private ConcurrentMap<K, V> map;
  private K key;

  public PutValue(final ConcurrentMap<K, V> map, final K key) {
    this.map = map;
    this.key = key;
  }

  public AtomicPut<K, V> value(V value) {
    return new AtomicPut<K, V>(map, key, value);
  }
}
