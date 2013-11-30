package com.github.staslev.concurrent.nonblocking;

/**
 * Provides a set of atomic and non blocking operations on data structures.
 */
public class NonBlockingOperations {

  /**
   * Produces an aggregated value based on an input and a previous value.
   * 
   * @param <TInput> The type of the input.
   * @param <TValue> The type of the output value.
   */
  public interface Aggregator<TInput, TValue> {

    /**
     * Produces an aggregated value based on an input and a previous value.
     * 
     * @param input The new input value to be taken into account when producing an aggregated result.
     * @param previousValue The aggregated results up until now.
     * @return A new aggregated results based on the input and previous value.
     */
    TValue aggregate(TInput input, TValue previousValue);
  }

  public interface Transformer<V> {

    /**
    * Transforms an existing value into a new value.
    * @param value The existing value to transform.
    * @return A transformed value.
    */
    V transform(V value);
  }

  private NonBlockingOperations() {
  }

  /**
   * Provides a set of atomic and non blocking operations on concurrent maps.
   */
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
