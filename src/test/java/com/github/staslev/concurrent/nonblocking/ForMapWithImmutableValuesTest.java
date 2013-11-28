package com.github.staslev.concurrent.nonblocking;

import com.google.common.base.Function;
import org.junit.Test;

import javax.annotation.Nullable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ForMapWithImmutableValuesTest {

  public static final String KEY_WAS_NOT_PRESENT = "key_was_not_present";
  public static final String KEY_WAS_PRESENT = "key_was_present";
  private ConcurrentMap<Integer, String> mapUnderTest = new ConcurrentHashMap<Integer, String>();
  private String initialValue = "testValue";
  private Integer key = 1;
  private Function<String, String> oldOrNullToNewValueTransformer = new Function<String, String>() {
    @Override
    public String apply(@Nullable final String input) {
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
}
