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

import java.util.Optional;
import java.util.Set;

interface ContextHelper {

    String WRAPPER_CLASS_NAME_PREFIX = "Unmodifiable";

    String getDomainSimpleClassName(Context context);

    String getWrapperSimpleClassName(Context context);

    String getDomainClassName(Context context);

    String getWrapperClassName(Context context);

    Optional<String> getPackageName(Context context);

    Set<MyMethod> getAllOverridableMethods(Context context);

    Set<MyMethod> getOtherMethods(Context context);

    Set<MyMethod> getModificationMethods(Context context);

}
