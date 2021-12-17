/*
 * Copyright 2021 Alexengrig Dev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.alexengrig.util.annotation.processor;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MyContextHelperTest {

    @Test
    void testRegex() {
        Pattern regex = MyContextHelper.SETTER_METHOD_REGEX;
        assertTrue(regex.matcher("setX").matches());
        assertTrue(regex.matcher("setXXX").matches());
        assertTrue(regex.matcher("setValue").matches());
        assertFalse(regex.matcher("set").matches());
        assertFalse(regex.matcher("unsetValue").matches());
    }

    @Test
    void testIsSetter() {
        MyMethod method = new MyMethod("", "void", "setX", List.of(new MyMethod.Parameter("Object", "o")));
        assertTrue(MyContextHelper.isSetter(method));
        method = new MyMethod("", "void", "setX", List.of());
        assertFalse(MyContextHelper.isSetter(method));
        method = new MyMethod("", "Object", "setX", List.of(new MyMethod.Parameter("Object", "o")));
        assertFalse(MyContextHelper.isSetter(method));
        method = new MyMethod("", "void", "set", List.of(new MyMethod.Parameter("Object", "o")));
        assertFalse(MyContextHelper.isSetter(method));
    }

}