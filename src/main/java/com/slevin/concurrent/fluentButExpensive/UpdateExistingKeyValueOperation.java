package com.slevin.concurrent.fluentButExpensive;

import java.util.concurrent.ConcurrentMap;

public class UpdateExistingKeyValueOperation<K, V> {

  private ConcurrentMap<K, V> map;

  public UpdateExistingKeyValueOperation(final ConcurrentMap<K, V> map) {
    this.map = map;
  }

  public UpdateExistingValue<K, V> key(K key) {
    return new UpdateExistingValue<K, V>(map, key);
  }
}
