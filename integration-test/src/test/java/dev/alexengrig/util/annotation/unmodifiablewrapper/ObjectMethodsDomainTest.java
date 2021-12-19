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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ObjectMethodsDomainTest {

    @Test
    void testEquals() {
        ObjectMethodsDomain domain = new ObjectMethodsDomain();
        UnmodifiableObjectMethodsDomain wrapper = new UnmodifiableObjectMethodsDomain(domain);
        assertEquals(wrapper, wrapper);
        assertEquals(wrapper, new UnmodifiableObjectMethodsDomain(domain));
        assertNotEquals(domain, wrapper);
        assertNotEquals(wrapper, domain);
    }

    @Test
    void testHashCode() {
        ObjectMethodsDomain domain = new ObjectMethodsDomain();
        UnmodifiableObjectMethodsDomain wrapper = new UnmodifiableObjectMethodsDomain(domain);
        assertEquals(domain.hashCode(), wrapper.hashCode());
    }

    @Test
    void testToString() {
        ObjectMethodsDomain domain = new ObjectMethodsDomain();
        UnmodifiableObjectMethodsDomain wrapper = new UnmodifiableObjectMethodsDomain(domain);
        assertEquals(wrapper.getClass().getSimpleName() + "{" + domain + "}", wrapper.toString());
    }

}