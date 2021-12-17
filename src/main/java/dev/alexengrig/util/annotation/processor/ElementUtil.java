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

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.Types;
import java.util.Optional;
import java.util.stream.Stream;

final class ElementUtil {

    private static final String JAVA_OBJECT_CLASS_NAME = Object.class.getName();

    private ElementUtil() throws IllegalAccessException {
        throw new IllegalAccessException("This is utility class");
    }

    static Optional<String> getPackageName(TypeElement type) {
        String className = type.getQualifiedName().toString();
        int lastIndexOfDot = className.lastIndexOf('.');
        if (lastIndexOfDot >= 0) {
            return Optional.of(className.substring(0, lastIndexOfDot));
        }
        return Optional.empty();
    }

    static String getClassName(TypeElement type) {
        return type.getQualifiedName().toString();
    }

    static String getSimpleClassName(TypeElement type) {
        return type.getSimpleName().toString();
    }

    static Stream<ExecutableElement> getAllMethods(TypeElement type) {
        return type.getEnclosedElements().stream()
                .filter(e -> ElementKind.METHOD.equals(e.getKind()))
                .map(ExecutableElement.class::cast);
    }

    static Optional<TypeElement> getParentClass(Types typeUtils, TypeElement type) {
        return typeUtils.directSupertypes(type.asType()).stream()
                .limit(1) // parent class is first
                .map(DeclaredType.class::cast)
                .map(DeclaredType::asElement)
                .filter(element -> ElementKind.CLASS == element.getKind())
                .filter(element -> !JAVA_OBJECT_CLASS_NAME.equals(element.toString()))
                .map(TypeElement.class::cast)
                .findFirst();
    }

}
