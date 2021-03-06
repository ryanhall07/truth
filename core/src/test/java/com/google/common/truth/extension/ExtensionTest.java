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
package com.google.common.truth.extension;

import static com.google.common.truth.extension.ExtendedVerb.ASSERT;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * A test that's more or less intended to show how one uses an extended verb.
 *
 * @author David Saff
 * @author Christian Gruber (cgruber@israfil.net)
 */
@RunWith(JUnit4.class)
public class ExtensionTest {
  @Test public void customTypeProposition() {
    ASSERT.that(new MyType(5)).matches(new MyType(2 + 3));
  }

  @Test public void customTypePropositionWithFailure() {
    try {
      ASSERT.that(new MyType(5)).matches(new MyType(4));
      ASSERT.fail("Should have thrown.");
    } catch (AssertionError e) {
      ASSERT.that(e.getMessage()).contains("Not true that");
      ASSERT.that(e.getMessage()).contains("matches");
    }
  }

}
