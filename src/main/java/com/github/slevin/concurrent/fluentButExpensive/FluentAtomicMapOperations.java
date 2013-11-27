package com.github.slevin.concurrent.fluentButExpensive;

import java.util.concurrent.ConcurrentMap;

public class FluentAtomicMapOperations {

  public static <K, V> KeyOperation<K, V> map(ConcurrentMap<K, V> map) {
    return new KeyOperation<K, V>(map);
  }

  public static <K> CounterKeyOperation<K> counterMap(ConcurrentMap<K, Long> map) {
      return new CounterKeyOperation<K>(map);
  }
}
