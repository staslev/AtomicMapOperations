package com.github.staslev.concurrent.nonblocking;

public class NonBlockingOperations {

  private NonBlockingOperations() {
  }

  public static class forMap {

    private static final AtomicMapOperationsForImmutableValues FOR_IMMUTABLE_VALUES = new AtomicMapOperationsForImmutableValues();
    private static final AtomicMapOperationsForLongValues FOR_LONG_VALUES = new AtomicMapOperationsForLongValues();

    private forMap() {
    }

    /**
    * Provides atomic operations for concurrent maps with immutable values.
    * @return An instance providing a set of atomic operations supported for maps with immutable values.
    */
    public static AtomicMapOperationsForImmutableValues withImmutableValues() {
      return FOR_IMMUTABLE_VALUES;
    }

    /**
     * Provides atomic operations for concurrent maps with long values, typically used when counting things.
     * @return An instance providing a set of atomic operations supported for maps with values of type Long.
     */
    public static AtomicMapOperationsForLongValues withLongValues() {
      return FOR_LONG_VALUES;
    }
  }
}
