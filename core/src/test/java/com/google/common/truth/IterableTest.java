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
import static java.util.Arrays.asList;
import static org.junit.Assert.fail;

import com.google.common.collect.ImmutableList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Tests for Collection Subjects.
 *
 * @author David Saff
 * @author Christian Gruber (cgruber@israfil.net)
 */
@RunWith(JUnit4.class)
public class IterableTest {

  @Test public void hasSize() {
    assertThat(ImmutableList.of(1, 2, 3)).hasSize(3);
  }

  @Test public void hasSizeZero() {
    assertThat(ImmutableList.of()).hasSize(0);
  }

  @Test public void hasSizeNegative() {
    try {
      assertThat(ImmutableList.of(1, 2, 3)).hasSize(-1);
      fail();
    } catch (IllegalArgumentException expected) {
    }
  }

  @Test public void iteratesOver() {
    assertThat(iterable(1, 2, 3)).iteratesAs(1, 2, 3);
  }

  @Test public void iteratesOverAsList() {
    assertThat(iterable(1, 2, 3)).iteratesAs(asList(1, 2, 3));
  }

  @Test public void iteratesOverSequence_Legacy() {
    assertThat(iterable(1, 2, 3)).iteratesOverSequence(1, 2, 3);
  }

  @Test public void iteratesOverEmpty() {
    assertThat(iterable()).iteratesAs();
  }

  @Test public void iteratesOverWithOrderingFailure() {
    try {
      assertThat(iterable(1, 2, 3)).iteratesAs(2, 3, 1);
      fail("Should have thrown.");
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("Not true that");
    }
  }

  @Test public void iteratesOverWithTooManyItemsFailure() {
    try {
      assertThat(iterable(1, 2, 3)).iteratesAs(1, 2, 3, 4);
      fail("Should have thrown.");
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("Not true that");
    }
  }

  @Test public void iteratesOverWithTooFewItemsFailure() {
    try {
      assertThat(iterable(1, 2, 3)).iteratesAs(1, 2);
      fail("Should have thrown.");
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("Not true that");
    }
  }

  @Test public void iteratesOverWithIncompatibleItems() {
    try {
      assertThat(iterable(1, 2, 3)).iteratesAs(1, 2, "a");
      fail("Should have thrown.");
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("Not true that");
    }
  }

  @SuppressWarnings("unchecked")
  @Test public void iteratesOverAsListWithIncompatibleItems() {
    try {
      assertThat(iterable(1, 2, 3)).iteratesAs(asList(1, 2, "a"));
      fail("Should have thrown.");
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("Not true that");
    }
  }

  @Test public void iterableHasItem() {
    assertThat(iterable(1, 2, 3)).contains(1);
  }

  @Test public void iterableHasItemWithNull() {
    assertThat(iterable(1, null, 3)).contains(null);
  }

  @Test public void iterableHasItemFailure() {
    try {
      assertThat(iterable(1, 2, 3)).contains(5);
      fail("Should have thrown.");
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("Not true that");
    }
  }

  @Test public void iterableDoesntHaveItem() {
    assertThat(iterable(1, null, 3)).doesNotContain(5);
  }

  @Test public void iterableDoesntHaveItemWithNull() {
    assertThat(iterable(1, 2, 3)).doesNotContain(null);
  }

  @Test public void iterableDoesntHaveItemFailure() {
    try {
      assertThat(iterable(1, 2, 3)).doesNotContain(2);
      fail("Should have thrown.");
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("Not true that");
    }
  }

  @Test public void iterableHasAnyOf() {
    assertThat(iterable(1, 2, 3)).containsAnyOf(1, 5);
  }

  @Test public void iterableHasAnyOfWithNull() {
    assertThat(iterable(1, null, 3)).containsAnyOf(null, 5);
  }

  @Test public void iterableHasAnyOfWithNullInThirdAndFinalPosition() {
    assertThat(iterable(1, null, 3)).containsAnyOf(4, 5, (Integer) null);
  }

  @Test public void iterableHasAnyOfFailure() {
    try {
      assertThat(iterable(1, 2, 3)).containsAnyOf(5, 6, 0);
      fail("Should have thrown.");
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("Not true that");
    }
  }

  @Test public void iterableHasAllOfWithMany() {
    assertThat(iterable(1, 2, 3)).containsAllOf(1, 2);
  }

  @Test public void iterableHasAllOfWithDuplicates() {
    assertThat(iterable(1, 2, 2, 2, 3)).containsAllOf(2, 2);
  }

  @Test public void iterableHasAllOfWithNull() {
    assertThat(iterable(1, null, 3)).containsAllOf(3, (Integer) null);
  }

  @Test public void iterableHasAllOfWithNullAtThirdAndFinalPosition() {
    assertThat(iterable(1, null, 3)).containsAllOf(1, 3, null);
  }

  @Test public void iterableHasAllOfFailure() {
    try {
      assertThat(iterable(1, 2, 3)).containsAllOf(1, 2, 4);
      fail("Should have thrown.");
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("Not true that");
      assertThat(e.getMessage()).contains("is missing");
      assertThat(e.getMessage()).contains("4");
    }
  }

  @Test public void iterableHasAllOfWithDuplicatesFailure() {
    try {
      assertThat(iterable(1, 2, 3)).containsAllOf(1, 2, 2, 2, 3, 4);
      fail("Should have thrown.");
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("Not true that");
      assertThat(e.getMessage()).contains("contains all of");
      assertThat(e.getMessage()).contains("is missing");
      assertThat(e.getMessage()).contains("2 [2 copies], 4");
    }
  }

  /*
   * Slightly subtle test to ensure that if multiple equal elements are found
   * to be missing we only reference it once in the output message.
   */
  @Test public void iterableHasAllOfWithDuplicateMissingElements() {
    try {
      assertThat(iterable(1, 2)).containsAllOf(4, 4, 4);
      fail("Should have thrown.");
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("Not true that");
      assertThat(e.getMessage()).contains("is missing");
      assertThat(e.getMessage()).contains("4 [3 copies]");
    }
  }

  @Test public void iterableHasAllOfWithNullFailure() {
    try {
      assertThat(iterable(1, null, 3)).containsAllOf(1, null, null, 3);
      fail("Should have thrown.");
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("Not true that");
      assertThat(e.getMessage()).contains("is missing");
      assertThat(e.getMessage()).contains("null");
    }
  }

  @Test public void iterableHasAllOfInOrder() {
    assertThat(iterable(3, 2, 5)).containsAllOf(3, 2, 5).inOrder();
  }

  @Test public void iterableHasAllOfInOrderWithNull() {
    assertThat(iterable(3, null, 5)).containsAllOf(3, null, 5).inOrder();
  }

  @Test public void iterableHasAllOfInOrderWithFailure() {
    try {
      assertThat(iterable(1, null, 3)).containsAllOf(null, 1, 3).inOrder();
      fail("Should have thrown.");
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("Not true that");
      assertThat(e.getMessage()).contains("contains all elements in order");
    }
  }

  @Test public void iterableHasNoneOf() {
    assertThat(iterable(1, 2, 3)).containsNoneOf(4, 5, 6);
  }

  @Test public void iterableHasNoneOfFailure() {
    try {
      assertThat(iterable(1, 2, 3)).containsNoneOf(1, 2, 4);
      fail("Should have thrown.");
    } catch (AssertionError e) {
      assertThat(e.getMessage()).isEqualTo(
          "Not true that <[1, 2, 3]> contains none of <[1, 2, 4]>. It contains <[1, 2]>");
    }
  }

  @Test public void iterableHasNoneOfFailureWithDuplicateInSubject() {
    try {
      assertThat(iterable(1, 2, 2, 3)).containsNoneOf(1, 2, 4);
      fail("Should have thrown.");
    } catch (AssertionError e) {
      assertThat(e.getMessage()).isEqualTo(
          "Not true that <[1, 2, 2, 3]> contains none of <[1, 2, 4]>. It contains <[1, 2]>");
    }
  }

  @Test public void iterableHasNoneOfFailureWithDuplicateInExpected() {
    try {
      assertThat(iterable(1, 2, 3)).containsNoneOf(1, 2, 2, 4);
      fail("Should have thrown.");
    } catch (AssertionError e) {
      assertThat(e.getMessage()).isEqualTo(
          "Not true that <[1, 2, 3]> contains none of <[1, 2, 2, 4]>. It contains <[1, 2]>");
    }
  }

  @Test public void iterableHasExactlyWithMany() {
    assertThat(iterable(1, 2, 3)).containsOnlyElements(1, 2, 3);
  }

  @Test public void iterableHasExactlyOutOfOrder() {
    assertThat(iterable(1, 2, 3, 4)).containsOnlyElements(3, 1, 4, 2);
  }

  @Test public void iterableHasExactlyWithDuplicates() {
    assertThat(iterable(1, 2, 2, 2, 3)).containsOnlyElements(1, 2, 2, 2, 3);
  }

  @Test public void iterableHasExactlyWithDuplicatesOutOfOrder() {
    assertThat(iterable(1, 2, 2, 2, 3)).containsOnlyElements(2, 1, 2, 3, 2);
  }

  @Test public void iterableHasExactlyWithNull() {
    assertThat(iterable(1, null, 3)).containsOnlyElements(1, null, 3);
  }

  @Test public void iterableHasExactlyWithNullOutOfOrder() {
    assertThat(iterable(1, null, 3)).containsOnlyElements(1, 3, (Integer) null);
  }

  @Test public void iterableHasExactlyMissingItemFailure() {
    try {
      assertThat(iterable(1, 2)).containsOnlyElements(1, 2, 4);
      fail("Should have thrown.");
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("Not true that");
      assertThat(e.getMessage()).contains("is missing");
      assertThat(e.getMessage()).contains("4");
    }
  }

  @Test public void iterableHasExactlyUnexpectedItemFailure() {
    try {
      assertThat(iterable(1, 2, 3)).containsOnlyElements(1, 2);
      fail("Should have thrown.");
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("Not true that");
      assertThat(e.getMessage()).contains("has unexpected items");
      assertThat(e.getMessage()).contains("3");
    }
  }

  @Test public void iterableHasExactlyWithDuplicatesNotEnoughItemsFailure() {
    try {
      assertThat(iterable(1, 2, 3)).containsOnlyElements(1, 2, 2, 2, 3);
      fail("Should have thrown.");
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("Not true that");
      assertThat(e.getMessage()).contains("contains only");
      assertThat(e.getMessage()).contains("is missing");
      assertThat(e.getMessage()).contains("2 [2 copies]");
    }
  }

  @Test public void iterableHasExactlyWithDuplicatesMissingItemFailure() {
    try {
      assertThat(iterable(1, 2, 3)).containsOnlyElements(1, 2, 2, 2, 3, 4);
      fail("Should have thrown.");
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("Not true that");
      assertThat(e.getMessage()).contains("contains only");
      assertThat(e.getMessage()).contains("is missing");
      assertThat(e.getMessage()).contains("2 [2 copies], 4");
    }
  }

  @Test public void iterableHasExactlyWithDuplicatesUnexpectedItemFailure() {
    try {
      assertThat(iterable(1, 2, 2, 2, 2, 3)).containsOnlyElements(1, 2, 2, 3);
      fail("Should have thrown.");
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("Not true that");
      assertThat(e.getMessage()).contains("contains only");
      assertThat(e.getMessage()).contains("has unexpected items");
      assertThat(e.getMessage()).contains("2 [2 copies]");
    }
  }

  /*
   * Slightly subtle test to ensure that if multiple equal elements are found
   * to be missing we only reference it once in the output message.
   */
  @Test public void iterableHasExactlyWithDuplicateMissingElements() {
    try {
      assertThat(iterable()).containsOnlyElements(4, 4, 4);
      fail("Should have thrown.");
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("Not true that");
      assertThat(e.getMessage()).contains("is missing");
      assertThat(e.getMessage()).contains("4 [3 copies]");
    }
  }

  @Test public void iterableHasExactlyWithNullFailure() {
    try {
      assertThat(iterable(1, null, 3)).containsOnlyElements(1, null, null, 3);
      fail("Should have thrown.");
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("Not true that");
      assertThat(e.getMessage()).contains("is missing");
      assertThat(e.getMessage()).contains("null");
    }
  }

  @Test public void iterableHasExactlyInOrder() {
    assertThat(iterable(3, 2, 5)).containsOnlyElements(3, 2, 5).inOrder();
  }

  @Test public void iterableHasExactlyInOrderWithNull() {
    assertThat(iterable(3, null, 5)).containsOnlyElements(3, null, 5).inOrder();
  }

  @Test public void iterableHasExactlyInOrderWithFailure() {
    try {
      assertThat(iterable(1, null, 3)).containsOnlyElements(null, 1, 3).inOrder();
      fail("Should have thrown.");
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("Not true that");
      assertThat(e.getMessage()).contains("contains only these elements in order");
    }
  }

  @Test public void iterableIsEmpty() {
    assertThat(iterable()).isEmpty();
  }

  @Test public void iterableIsEmptyWithFailure() {
    try {
      assertThat(iterable(1, null, 3)).isEmpty();
      fail("Should have thrown.");
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("Not true that");
      assertThat(e.getMessage()).contains("is empty");
    }
  }

  @Test public void iterableIsNotEmpty() {
    assertThat(iterable("foo")).isNotEmpty();
  }

  @Test public void iterableIsNotEmptyWithFailure() {
    try {
      assertThat(iterable()).isNotEmpty();
      fail("Should have thrown.");
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("Not true that");
      assertThat(e.getMessage()).contains("is not empty");
    }
  }

  /**
   * This tests the rather unwieldly case where someone alters the
   * collection out from under the Subject before inOrder() is called.
   */
  @Test public void iterableHasAllOfInOrderHackedWithTooManyItemsFailure() {
    ArrayList<Integer> list = new ArrayList<Integer>(asList(1, null, 3));
    Ordered o = assertThat((Iterable<Integer>) list).containsAllOf(1, null, 3);
    list.add(6);
    validateHackedFailure(o);
  }

  /**
   * This tests the rather unwieldly case where someone alters the
   * collection out from under the Subject before inOrder() is called.
   */
  @Test public void iterableHasAllOfInOrderHackedWithTooFewItemsFailure() {
    ArrayList<Integer> list = new ArrayList<Integer>(asList(1, null, 3));
    Ordered o = assertThat((Iterable<Integer>) list).containsAllOf(1, null, 3);
    list.remove(1);
    validateHackedFailure(o);
  }

  /**
   * This tests the rather unwieldly case where someone alters the
   * collection out from under the Subject before inOrder() is called.
   */
  @Test public void iterableHasAllOfInOrderHackedWithNoItemsFailure() {
    ArrayList<Integer> list = new ArrayList<Integer>(asList(1, null, 3));
    Ordered o = assertThat((Iterable<Integer>) list).containsAllOf(1, null, 3);
    list.clear();
    validateHackedFailure(o);
  }

  /**
   * This tests the rather unwieldly case where someone alters the
   * collection out from under the Subject before inOrder() is called.
   */
  @Test public void iterableHasExactlyInOrderHackedWithTooManyItemsFailure() {
    ArrayList<Integer> list = new ArrayList<Integer>(asList(1, null, 3));
    Ordered o = assertThat((Iterable<Integer>) list).containsOnlyElements(1, null, 3);
    list.add(6);
    validateHackedFailure(o);
  }

  /**
   * This tests the rather unwieldly case where someone alters the
   * collection out from under the Subject before inOrder() is called.
   */
  @Test public void iterableHasExactlyInOrderHackedWithTooFewItemsFailure() {
    ArrayList<Integer> list = new ArrayList<Integer>(asList(1, null, 3));
    Ordered o = assertThat((Iterable<Integer>) list).containsOnlyElements(1, null, 3);
    list.remove(1);
    validateHackedFailure(o);
  }

  /**
   * This tests the rather unwieldly case where someone alters the
   * collection out from under the Subject before inOrder() is called.
   */
  @Test public void iterableHasExactlyInOrderHackedWithNoItemsFailure() {
    ArrayList<Integer> list = new ArrayList<Integer>(asList(1, null, 3));
    Ordered o = assertThat((Iterable<Integer>) list).containsOnlyElements(1, null, 3);
    list.clear();
    validateHackedFailure(o);
  }

  /** Factored out failure condition for "hacked" failures of inOrder() */
  private void validateHackedFailure(Ordered ordered) {
    try {
      ordered.inOrder();
      fail("Should have thrown.");
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("Not true that");
      assertThat(e.getMessage()).contains("in order");
    }
  }

  /**
   * Helper that returns a general Collection rather than a List.
   * This ensures that we test CollectionSubject (rather than ListSubject).
   */
  private static Iterable<?> iterable(Object... items) {
    return Arrays.asList(items);
  }

}
