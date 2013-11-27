package com.github.slevin.concurrent;

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

  public <K, V> void putOrTransform(final ConcurrentMap<K, V> map, final K key, final Function<V, V> oldOrNullToNewValueTransformer) {

    V previousValueOrNull;
    V nextValue;

    do {
      previousValueOrNull = map.get(key);
      nextValue = oldOrNullToNewValueTransformer.apply(previousValueOrNull);
    } while (!atomicallyUpdateExistingEntry(map, key, previousValueOrNull, nextValue)
        && !atomicallyInsertNewEntry(map, key, previousValueOrNull, nextValue));
  }

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
