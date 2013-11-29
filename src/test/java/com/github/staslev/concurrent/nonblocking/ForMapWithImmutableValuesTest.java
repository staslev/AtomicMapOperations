package com.github.staslev.concurrent.nonblocking;

import com.google.common.base.Function;
import org.hamcrest.Matcher;
import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ForMapWithImmutableValuesTest {

  public static final String KEY_WAS_NOT_PRESENT = "key_was_not_present";
  public static final String KEY_WAS_PRESENT = "key_was_present";
  private ConcurrentMap<Integer, String> mapUnderTest = new ConcurrentHashMap<Integer, String>();
  private String initialValue = "testValue";
  private Integer key = 1;
  private Function<String, String> oldOrNullToNewValueTransformer = new Function<String, String>() {
    @Override
    public String apply(final String input) {
      return input == null ? KEY_WAS_NOT_PRESENT : KEY_WAS_PRESENT;
    }
  };

  @Test
  public void test_WhenKeyNotPresent_TransformerResultAssignedToKey() throws Exception {

    mapUnderTest.put(key, initialValue);

    NonBlockingOperations.forMap.withImmutableValues().putOrTransform(mapUnderTest, key, oldOrNullToNewValueTransformer);

    assertThat(mapUnderTest.get(key), is(KEY_WAS_PRESENT));
  }

  @Test
  public void test_WhenKeyIsPresent_TransformerResultAssignedToKey() throws Exception {

    NonBlockingOperations.forMap.withImmutableValues().putOrTransform(mapUnderTest, key, oldOrNullToNewValueTransformer);

    assertThat(mapUnderTest.get(key), is(KEY_WAS_NOT_PRESENT));
  }

  @Test
  public void test_WhenKeyIsPresent_AggregatorResultAssignedToKey() throws Exception {

    mapUnderTest.put(key, initialValue);

    final AtomicMapOperationsForImmutableValues.Aggregator<Integer, String> aggregator = new AtomicMapOperationsForImmutableValues.Aggregator<Integer, String>() {
      @Override
      public String aggregate(final Integer input, final String previousValue) {
        return previousValue != null && input != null && input > 0 ? input.toString() + " " + previousValue : previousValue;
      }
    };

    final int input = 1;
    NonBlockingOperations.forMap.withImmutableValues().putOrTransform(mapUnderTest, key, aggregator, input);

    final String expected = Integer.toString(input) + " " + initialValue;
    assertThat(mapUnderTest.get(key), is(expected));
  }

  @Test
  public void test_WhenKeyNotPresent_AggregatorResultAssignedToKey() throws Exception {

    mapUnderTest.put(key, initialValue);

    final AtomicMapOperationsForImmutableValues.Aggregator<Integer, String> aggregator = new AtomicMapOperationsForImmutableValues.Aggregator<Integer, String>() {
      @Override
      public String aggregate(final Integer input, final String previousValue) {
        return previousValue != null && input != null && input > 0 ? input.toString() + " " + previousValue : previousValue;
      }
    };

    final int input = -1;
    NonBlockingOperations.forMap.withImmutableValues().putOrTransform(mapUnderTest, key, aggregator, input);

    assertThat(mapUnderTest.get(key), is(initialValue));
  }
}
