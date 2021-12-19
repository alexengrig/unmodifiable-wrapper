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

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class NotOverridableMethodsDomainTest {

    @Test
    void testForMethods() {
        NotOverridableMethodsDomain domain = new NotOverridableMethodsDomain();
        UnmodifiableNotOverridableMethodsDomain wrapper = new UnmodifiableNotOverridableMethodsDomain(domain);
        assertNotNull(wrapper);
        Method[] methods = UnmodifiableNotOverridableMethodsDomain.class.getDeclaredMethods();
        Map<String, Method> methodByName = Arrays.stream(methods)
                .collect(Collectors.toMap(Method::getName, Function.identity()));
        wrapper.set(null);
        assertFalse(methodByName.containsKey("staticMethod"));
        assertFalse(methodByName.containsKey("getPrivateInteger"));
        assertFalse(methodByName.containsKey("setPrivateInteger"));
        assertFalse(methodByName.containsKey("getFinalInteger"));
        assertFalse(methodByName.containsKey("setFinalInteger"));
    }

}