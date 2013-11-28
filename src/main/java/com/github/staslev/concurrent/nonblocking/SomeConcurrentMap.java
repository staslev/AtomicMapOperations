package com.github.staslev.concurrent.nonblocking;

public class SomeConcurrentMap {

  /**
   * Provides atomic operations for concurrent maps with immutable values.
   * @return An instance providing a set of atomic operations supported for maps with immutable values.
   */
  public static AtomicMapOperationsForImmutableValues withImmutableValues() {
    return new AtomicMapOperationsForImmutableValues();
  }

  /**
   * Provides atomic operations for concurrent maps with long values, typically used when counting things.
    * @return An instance providing a set of atomic operations supported for maps with values of type Long.
   */
  public static AtomicMapOperationsForLongValues withLongValues() {
    return new AtomicMapOperationsForLongValues();
  }
}
