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

import dev.alexengrig.util.annotation.UnmodifiableWrapper;

@UnmodifiableWrapper
public class NotOverridableMethodsDomain {

    private int integer;

    public static void staticMethod() {
        // do nothing
    }

    public void set(Object o) {
        // do nothing
    }

    private int getPrivateInteger() {
        return integer;
    }

    private void setPrivateInteger(int integer) {
        this.integer = integer;
    }

    public final int getFinalInteger() {
        return integer;
    }

    public final void setFinalInteger(int integer) {
        this.integer = integer;
    }

}
