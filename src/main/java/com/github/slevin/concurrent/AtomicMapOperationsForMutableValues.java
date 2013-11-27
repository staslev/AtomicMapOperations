package com.github.slevin.concurrent;

import com.google.common.base.Function;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;

public class AtomicMapOperationsForMutableValues {

  AtomicMapOperationsForMutableValues() {
  }

  public <K, V> void putOrMutate(final ConcurrentMap<K, V> map, final K key, final Callable<V> newValueGenerator,
      final Function<V, Boolean> nonNullValueMutator) {

    V previousValueOrNull;
    boolean eitherPutOrMutated;

    do {
      previousValueOrNull = map.get(key);
      if (previousValueOrNull != null) {
        final V previousValue = previousValueOrNull;
        eitherPutOrMutated = nonNullValueMutator.apply(previousValue);
      } else {
        try {
            final V initialValue = newValueGenerator.call();
            eitherPutOrMutated = map.putIfAbsent(key, initialValue) == null;
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      }
    } while (!eitherPutOrMutated);
  }

  public <K, V> boolean mutateIfPresent(final ConcurrentMap<K, V> map, final K key, final Function<V, Boolean> nonNullOldValueMutator) {

    V previousValueOrNull;
    boolean put;

    do {
      previousValueOrNull = map.get(key);
      if (previousValueOrNull != null) {
        final V nonNullPreviousValue = previousValueOrNull;
        put = nonNullOldValueMutator.apply(nonNullPreviousValue);
      } else {
        return false;
      }
    } while (!put);

    return true;
  }
}
