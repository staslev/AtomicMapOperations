package com.slevin.concurrent.fluentButExpensive;

import java.util.concurrent.ConcurrentMap;

public class AtomicPut<K, V> {

  private final ConcurrentMap<K, V> map;
  private final K key;
  private final V value;

  AtomicPut(final ConcurrentMap<K, V> map, final K key, final V value) {
    this.map = map;
    this.key = key;
    this.value = value;
  }

  public boolean atomically() {
    return map.putIfAbsent(key, value) == null;
  }
}
