package com.github.staslev.concurrent.nonblocking.fluentButExpensive;

import java.util.concurrent.ConcurrentMap;

public class PutKeyValueOperation<K, V> {

  private ConcurrentMap<K, V> map;

  PutKeyValueOperation(final ConcurrentMap<K, V> map) {
    this.map = map;
  }

  public PutValue<K,V> key(K key) {
    return new PutValue<K, V>(map, key);
  }
}
