package com.github.staslev.concurrent.nonblocking.fluentButExpensive;

import java.util.concurrent.ConcurrentMap;

public class KeyOperation<K, V> {
  private final ConcurrentMap<K, V> map;

  KeyOperation(final ConcurrentMap<K, V> map) {
    this.map = map;
  }

  public PutKeyValueOperation<K,V> insertIfNew() {
    return new PutKeyValueOperation<K,V>(map);
  }

  public UpdateExistingKeyValueOperation<K,V> updateIfPresent() {
    return new UpdateExistingKeyValueOperation<K,V>(map);
  }

  public PutOrTransformKeyValueOperation<K,V> insertOrUpdate() {
    return new PutOrTransformKeyValueOperation<K,V>(map);
  }

}
