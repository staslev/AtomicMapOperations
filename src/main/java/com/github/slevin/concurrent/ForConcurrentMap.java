package com.github.slevin.concurrent;

public class ForConcurrentMap {

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
