package com.github.staslev.concurrent.nonblocking;

import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.junit.Assert.assertTrue;

public class ForMapWithLongValuesTest {

  private ConcurrentMap<String, Long> mapUnderTest = new ConcurrentHashMap<String, Long>();
  private String key = "key";
  private Long initialValue = 3L;

  @Test
  public void test_WhenKeyNotPresent_IncreasesValueByOne() throws Exception {

    NonBlockingOperations.forMap.withLongValues().increase(mapUnderTest, key);

    assertTrue(mapUnderTest.get(key).equals(1L));
  }

  @Test
  public void test_WhenKeyPresent_IncreasesValueByOne() throws Exception {

    mapUnderTest.put(key, initialValue);

    NonBlockingOperations.forMap.withLongValues().increase(mapUnderTest, key);

    assertTrue(mapUnderTest.get(key).equals(initialValue + 1L));
  }

  @Test
  public void test_WhenKeyPresent_ValueIncreasedByMany() throws Exception {

    mapUnderTest.put(key, initialValue);

    final Long increaseBy = 5L;
    NonBlockingOperations.forMap.withLongValues().increase(mapUnderTest, key, increaseBy);

    assertTrue(mapUnderTest.get(key).equals(initialValue + increaseBy));
  }

  @Test
  public void test_WhenKeyNotPresent_DecreasesValueByOne() throws Exception {

    NonBlockingOperations.forMap.withLongValues().decrease(mapUnderTest, key);

    assertTrue(mapUnderTest.get(key).equals(-1L));
  }

  @Test
  public void test_WhenKeyPresent_DecreasesValueByOne() throws Exception {

    mapUnderTest.put(key, initialValue);

    NonBlockingOperations.forMap.withLongValues().decrease(mapUnderTest, key);

    assertTrue(mapUnderTest.get(key).equals(initialValue - 1L));
  }

  @Test
  public void test_WhenKeyPresent_DecreasesValueByMay() throws Exception {

    mapUnderTest.put(key, initialValue);

    final Long decreaseBy = 5L;
    NonBlockingOperations.forMap.withLongValues().decrease(mapUnderTest, key, decreaseBy);

    assertTrue(mapUnderTest.get(key).equals(initialValue - decreaseBy));
  }
}
