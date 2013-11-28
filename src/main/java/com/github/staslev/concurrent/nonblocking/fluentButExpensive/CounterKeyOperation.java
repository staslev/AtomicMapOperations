package com.github.staslev.concurrent.nonblocking.fluentButExpensive;

import java.util.concurrent.ConcurrentMap;

public class CounterKeyOperation<K> {
  private final ConcurrentMap<K, Long> map;

  CounterKeyOperation(final ConcurrentMap<K, Long> map) {
    this.map = map;
  }

  public AtomicKeyIncrease<K> key(final K key) {
    return new AtomicKeyIncrease<K>(map, key);
  }
}
