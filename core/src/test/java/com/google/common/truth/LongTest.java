/*
 * Copyright (c) 2011 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.common.truth;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.TruthJUnit.assume;
import static org.junit.Assert.fail;

import com.google.common.truth.Expect;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.AssumptionViolatedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Tests for Long Subjects.
 *
 * @author David Saff
 * @author Christian Gruber (cgruber@israfil.net)
 */
@RunWith(JUnit4.class)
public class LongTest {
  @Rule public final Expect EXPECT = Expect.create();

  @Test public void simpleEquality() {
    assertThat(2L + 2).is(4L);
    assertThat(2L + 2).isEqualTo(4L);
  }

  @Test public void longIsLong() {
    assertThat(4L).is(4L);
  }

  @Test public void simpleInequality() {
    assertThat(2L + 2).isNotEqualTo(5L);
  }

  @Test public void equalityFail() {
    try {
      assertThat(2L + 2).isEqualTo(5L);
      fail("Should have thrown");
    } catch (AssertionError expected) {
      assertThat(expected.getMessage()).contains("Not true that <4> is equal to <5>");
    }
  }

  @Test public void inequalityFail() {
    try {
      assertThat(2L + 2).isNotEqualTo(4L);
      fail("Should have thrown");
    } catch (AssertionError expected) {
      assertThat(expected.getMessage()).contains("Not true that <4> is not equal to <4>");
    }
  }

  @Test public void additionAssumptionFail() {
    try {
      assume().that(2L + 2).isEqualTo(5L);
      fail("Should have thrown");
    } catch (AssumptionViolatedException expected) {}
  }

  @Test public void inclusiveRangeContainment() {
    EXPECT.that(2L).isInclusivelyInRange(2L, 4L);
    EXPECT.that(3L).isInclusivelyInRange(2L, 4L);
    EXPECT.that(4L).isInclusivelyInRange(2L, 4L);
  }

  @Test public void inclusiveRangeContainmentFailure() {
    try {
      assertThat(1L).isInclusivelyInRange(2L, 4L);
      fail("Should have thrown");
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("Not true that <1> is in <[2‥4]>");
    }
    try {
      assertThat(5L).isInclusivelyInRange(2L, 4L);
      fail("Should have thrown");
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("Not true that <5> is in <[2‥4]>");
    }
  }

  @Test public void inclusiveRangeContainmentInversionError() {
    try {
      assertThat(Long.MAX_VALUE).isInclusivelyInRange(4L, 2L);
      fail("Should have thrown");
    } catch (IllegalArgumentException e) {}
  }

  @Test public void exclusiveRangeContainment() {
    EXPECT.that(3L).isBetween(2L, 5L);
    EXPECT.that(4L).isBetween(2L, 5L);
  }

  @Test public void exclusiveRangeContainmentFailure() {
    try {
      assertThat(5L).isBetween(2L, 5L);
      fail("Should have thrown");
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("Not true that <5> is in <(2‥5)>");
    }
  }

  @Test public void exclusiveRangeContainmentInversionError() {
    try {
      assertThat(Long.MAX_VALUE).isBetween(5L, 2L);
      fail("Should have thrown");
    } catch (IllegalArgumentException e) {}
  }

  @Test public void equalityOfNulls() {
    assertThat((Long)null).isEqualTo((Long)null);
  }

  @Test public void equalityOfNullsFail() {
    try {
      assertThat((Long)null).isEqualTo(5L);
      fail("Should have thrown");
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("Not true that <null> is equal to <5>");
    }
    try {
      assertThat(5L).isEqualTo((Long)null);
      fail("Should have thrown");
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("Not true that <5> is equal to <null>");
    }
  }

  @Test public void inequalityOfNulls() {
    assertThat((Long)null).isNotEqualTo(4L);
    assertThat(4L).isNotEqualTo((Long)null);
  }

  @Test public void inequalityOfNullsFail() {
    try {
      assertThat((Long)null).isNotEqualTo((Long)null);
      fail("Should have thrown");
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("Not true that <null> is not equal to <null>");
    }
  }
}
