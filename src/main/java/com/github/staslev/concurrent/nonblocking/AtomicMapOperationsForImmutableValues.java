package com.github.staslev.concurrent.nonblocking;

import com.google.common.base.Function;

import java.util.concurrent.ConcurrentMap;

public class AtomicMapOperationsForImmutableValues {

  AtomicMapOperationsForImmutableValues() {
  }

  private <K, V> boolean atomicallyInsertNewEntry(final ConcurrentMap<K, V> map, final K key, final V previousValueOrNull, final V nextValue) {

    boolean newKeyInserted;

    try {
      newKeyInserted = previousValueOrNull == null && map.putIfAbsent(key, nextValue) == null;
    } catch (AssertionError assertionError) {
      newKeyInserted = false;
    }

    return newKeyInserted;
  }

  private <K, V> boolean atomicallyUpdateExistingEntry(final ConcurrentMap<K, V> map, final K key, final V previousValue, final V nextValue) {

    boolean exitingBucketIncreased;

    try {
      exitingBucketIncreased = previousValue != null && map.replace(key, previousValue, nextValue);
    } catch (AssertionError assertionError) {
      exitingBucketIncreased = false;
    }

    return exitingBucketIncreased;
  }

  /**
   * Atomically sets the value of <code>key</code> to the result of applying the <code>oldOrNullToNewValueTransformer</code>,
   * REGARDLESS of whether <code>key</code> WAS, OR WAS NOT, present in the map.
   *
   * @param map The map on each to perform the operation on.
   * @param key The key whose value is to be put or transformed.
   * @param oldOrNullToNewValueTransformer The transform function to be applied in order to produce values for the given key.
   *                                       This function WILL BE PASSED NULL in case the given key was not present in the map,
   *                                       in which case it is expected to produce an initial value.
   * @param <K> The type of the keys.
   * @param <V> The type of the values.
   */
  public <K, V> void putOrTransform(final ConcurrentMap<K, V> map, final K key, final Function<V, V> oldOrNullToNewValueTransformer) {

    V previousValueOrNull;
    V nextValue;

    do {
      previousValueOrNull = map.get(key);
      nextValue = oldOrNullToNewValueTransformer.apply(previousValueOrNull);
    } while (!atomicallyUpdateExistingEntry(map, key, previousValueOrNull, nextValue)
        && !atomicallyInsertNewEntry(map, key, previousValueOrNull, nextValue));
  }

  /**
   * In case <code>key</code> IS ALREADY PRESENT in the map, atomically sets its value to the value produced by applying the <code>oldOrNullToNewValueTransformer</code>.
   * In case the given key was not present in the map, no action will take place.
   *
   * @param map The map on each to perform the operation on.
   * @param key The key whose value is to be put or transformed, in case the given key already present in the map.
   * @param nonNullOldToNewValueTransformer The transform function to be applied on an EXISTING value corresponding to the given key.
   *                                        This function WILL NOT BE PASSED NULL values.
   * @param <K> The type of the keys.
   * @param <V> The type of the values.
   * @return true if the value of the given key was transformed, otherwise (if key was not present), false.
   */
  public <K, V> boolean transformIfPresent(final ConcurrentMap<K, V> map, final K key, final Function<V, V> nonNullOldToNewValueTransformer) {

    V previousValue;
    V nextValue;

    do {
      previousValue = map.get(key);
      if (previousValue != null) {
        nextValue = nonNullOldToNewValueTransformer.apply(previousValue);
      } else {
        return false;
      }
    } while (!atomicallyUpdateExistingEntry(map, key, previousValue, nextValue));

    return true;
  }

}
