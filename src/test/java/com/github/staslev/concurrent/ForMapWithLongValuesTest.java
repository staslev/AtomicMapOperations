package com.github.staslev.concurrent;

import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * FUNCTIONAL tests for NonBlockingOperations.forMap.withLongValues() factory method.
 * DOES NOT deal with performance benchmarking whatsoever.
 */
public class ForMapWithLongValuesTest {

  private ConcurrentMap<String, Long> mapUnderTest = new ConcurrentHashMap<String, Long>();
  private String key = "key";
  private Long initialValue = 3L;

  @Test
  public void test_WhenKeyNotPresentAndIncreased_ValueIsOne() throws Exception {

    NonBlockingOperations.forMap.withLongValues().increase(mapUnderTest, key);

    assertThat(mapUnderTest.get(key), is(1L));
  }

  @Test
  public void test_WhenKeyPresentAndIncreasedByOne_ValueIncreasedByOne() throws Exception {

    mapUnderTest.put(key, initialValue);

    NonBlockingOperations.forMap.withLongValues().increase(mapUnderTest, key);

    assertThat(mapUnderTest.get(key), is(initialValue + 1L));
  }

  @Test
  public void test_WhenKeyPresentAndIncreasedByMany_ValueIncreasedByMany() throws Exception {

    mapUnderTest.put(key, initialValue);

    final Long increaseBy = 5L;
    NonBlockingOperations.forMap.withLongValues().increase(mapUnderTest, key, increaseBy);

    assertThat(mapUnderTest.get(key), is(initialValue + increaseBy));
  }

  @Test
  public void test_WhenKeyNotPresentAndDecreased_ValueIsMinusOne() throws Exception {

    NonBlockingOperations.forMap.withLongValues().decrease(mapUnderTest, key);

    assertThat(mapUnderTest.get(key), is(-1L));
  }

  @Test
  public void test_WhenKeyPresentAndDecreased_ValueDecreasesByOne() throws Exception {

    mapUnderTest.put(key, initialValue);

    NonBlockingOperations.forMap.withLongValues().decrease(mapUnderTest, key);

    assertThat(mapUnderTest.get(key), is(initialValue - 1L));
  }

  @Test
  public void test_WhenKeyPresentAndDecreasedByMany_ValueIsDecreasesByMany() throws Exception {

    mapUnderTest.put(key, initialValue);

    final Long decreaseBy = 5L;
    NonBlockingOperations.forMap.withLongValues().decrease(mapUnderTest, key, decreaseBy);

    assertThat(mapUnderTest.get(key), is(initialValue - decreaseBy));
  }

}
