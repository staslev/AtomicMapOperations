package com.github.staslev.concurrent;

import com.github.staslev.concurrent.NonBlockingOperations;
import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * FUNCTIONAL tests for NonBlockingOperations.forMap.withImmutableValues() factory method.
 * DOES NOT deal with performance benchmarking whatsoever.
 */
public class ForMapWithImmutableValuesTest {

  public static final String KEY_WAS_NOT_PRESENT = "key_was_not_present";
  public static final String KEY_WAS_PRESENT = "key_was_present";
  private ConcurrentMap<Integer, String> mapUnderTest = new ConcurrentHashMap<Integer, String>();
  private String initialValue = "testValue";
  private Integer key = 1;
  private NonBlockingOperations.Transformer<String> oldOrNullToNewValueTransformer = new NonBlockingOperations.Transformer<String>() {
    @Override
    public String transform(final String input) {
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
  public void test_WhenKeyNotPresent_TransformerIfPresentDoesNotChangeValue() throws Exception {

    final String oldValue = mapUnderTest.get(key);

    NonBlockingOperations.forMap.withImmutableValues().transformIfPresent(mapUnderTest, key, oldOrNullToNewValueTransformer);

    assertThat(mapUnderTest.get(key), is(oldValue));
  }

  @Test
  public void test_WhenKeyIsPresent_TransformerResultAssignedToKey() throws Exception {

    NonBlockingOperations.forMap.withImmutableValues().putOrTransform(mapUnderTest, key, oldOrNullToNewValueTransformer);

    assertThat(mapUnderTest.get(key), is(KEY_WAS_NOT_PRESENT));
  }

  @Test
  public void test_WhenKeyIsPresent_AggregatorResultAssignedToKey() throws Exception {

    mapUnderTest.put(key, initialValue);

    final NonBlockingOperations.Aggregator<Integer, String> aggregator = new NonBlockingOperations.Aggregator<Integer, String>() {
      @Override
      public String aggregate(final Integer input, final String previousValue) {
        return previousValue != null && input != null && input > 0 ? input.toString() + " " + previousValue : previousValue;
      }
    };

    final int input = 1;
    NonBlockingOperations.forMap.withImmutableValues().putOrAggregate(mapUnderTest, key, aggregator, input);

    final String expected = Integer.toString(input) + " " + initialValue;
    assertThat(mapUnderTest.get(key), is(expected));
  }

  @Test
  public void test_WhenKeyNotPresent_AggregatorResultAssignedToKey() throws Exception {

    mapUnderTest.put(key, initialValue);

    final NonBlockingOperations.Aggregator<Integer, String> aggregator = new NonBlockingOperations.Aggregator<Integer, String>() {
      @Override
      public String aggregate(final Integer input, final String previousValue) {
        return previousValue != null && input != null && input > 0 ? input.toString() + " " + previousValue : previousValue;
      }
    };

    final int input = -1;
    NonBlockingOperations.forMap.withImmutableValues().putOrAggregate(mapUnderTest, key, aggregator, input);

    assertThat(mapUnderTest.get(key), is(initialValue));
  }

  @Test
  public void test_WhenKeyNotPresent_AggregateIfPresentDoesNotChangeValue() throws Exception {

    final NonBlockingOperations.Aggregator<Integer, String> aggregator = new NonBlockingOperations.Aggregator<Integer, String>() {
      @Override
      public String aggregate(final Integer input, final String previousValue) {
        return previousValue != null && input != null && input > 0 ? input.toString() + " " + previousValue : previousValue;
      }
    };

    final int input = 1;
    final String oldValue = mapUnderTest.get(key);

    NonBlockingOperations.forMap.withImmutableValues().aggregateIfPresent(mapUnderTest, key, aggregator, input);

    assertThat(mapUnderTest.get(key), is(oldValue));
  }
}
