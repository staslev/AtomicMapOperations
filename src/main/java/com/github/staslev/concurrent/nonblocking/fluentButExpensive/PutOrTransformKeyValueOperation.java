package com.github.staslev.concurrent.nonblocking.fluentButExpensive;

import java.util.concurrent.ConcurrentMap;

public class PutOrTransformKeyValueOperation<K, V> {
  private final ConcurrentMap<K, V> map;

  PutOrTransformKeyValueOperation(final ConcurrentMap<K, V> map) {
    this.map = map;
  }

  public PutOrTransformValue<K, V> key(final K key) {
    return new PutOrTransformValue<K, V>(map, key);
  }
}
