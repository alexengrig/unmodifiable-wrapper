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

package dev.alexengrig.util.annotation.unmodifiablewrapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AllTypesDomainTest {

    static void assertDomainEquals(AllTypesDomain domain, UnmodifiableAllTypesDomain wrapper) {
        assertEquals(domain.isBooleanValue(), wrapper.isBooleanValue(), "booleanValue");
        assertEquals(domain.getByteValue(), wrapper.getByteValue(), "byteValue");
        assertEquals(domain.getShortValue(), wrapper.getShortValue(), "shortValue");
        assertEquals(domain.getCharValue(), wrapper.getCharValue(), "charValue");
        assertEquals(domain.getIntValue(), wrapper.getIntValue(), "intValue");
        assertEquals(domain.getFloatValue(), wrapper.getFloatValue(), "floatValue");
        assertEquals(domain.getDoubleValue(), wrapper.getDoubleValue(), "doubleValue");
        assertEquals(domain.getLongValue(), wrapper.getLongValue(), "longValue");
        assertEquals(domain.getStringValue(), wrapper.getStringValue(), "stringValue");
    }

    static void assertUOEThrows(Executable executable, String message) {
        assertThrows(UnsupportedOperationException.class, executable, message);
    }

    @Test
    void testConstructor() {
        NullPointerException exception = assertThrows(NullPointerException.class, () ->
                new UnmodifiableAllTypesDomain(null));
        assertEquals("The delegate must not be null", exception.getMessage());
    }

    @Test
    void testEqualByGetters() {
        AllTypesDomain domain = new AllTypesDomain();
        UnmodifiableAllTypesDomain wrapper = new UnmodifiableAllTypesDomain(domain);
        assertDomainEquals(domain, wrapper);
    }

    @Test
    void testEqualAfterUpdating() {
        AllTypesDomain domain = new AllTypesDomain();
        UnmodifiableAllTypesDomain wrapper = new UnmodifiableAllTypesDomain(domain);
        domain.setBooleanValue(true);
        domain.setByteValue((byte) 1);
        domain.setShortValue((short) 2);
        domain.setCharValue((char) 3);
        domain.setIntValue(4);
        domain.setFloatValue(5);
        domain.setDoubleValue(6);
        domain.setLongValue(7);
        domain.setStringValue("8");
        assertDomainEquals(domain, wrapper);
    }

    @Test
    void testTryUpdate() {
        AllTypesDomain domain = new AllTypesDomain();
        UnmodifiableAllTypesDomain wrapper = new UnmodifiableAllTypesDomain(domain);
        assertUOEThrows(() -> wrapper.setBooleanValue(true), "booleanValue");
        assertUOEThrows(() -> wrapper.setByteValue((byte) 1), "byteValue");
        assertUOEThrows(() -> wrapper.setShortValue((short) 2), "shortValue");
        assertUOEThrows(() -> wrapper.setCharValue((char) 3), "charValue");
        assertUOEThrows(() -> wrapper.setIntValue(4), "intValue");
        assertUOEThrows(() -> wrapper.setFloatValue(5), "floatValue");
        assertUOEThrows(() -> wrapper.setDoubleValue(6), "doubleValue");
        assertUOEThrows(() -> wrapper.setLongValue(7), "longValue");
        assertUOEThrows(() -> wrapper.setStringValue("8"), "stringValue");
    }

}