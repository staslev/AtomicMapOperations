package com.slevin.concurrent;

public class SomeConcurrentMap {

  public static AtomicMapOperationsForImmutableValues withImmutableValues() {
    return new AtomicMapOperationsForImmutableValues();
  }

  public static AtomicMapOperationsForMutableValues withMutableValues() {
    return new AtomicMapOperationsForMutableValues();
  }

  public static AtomicMapOperationsForLongValues withLongValues() {
    return new AtomicMapOperationsForLongValues();
  }
}
