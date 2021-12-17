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
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeKind;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class MyMethod {

    private final String accessModifier;
    private final String returnType;
    private final String name;
    private final List<Parameter> parameters;

    MyMethod(String accessModifier, String returnType, String name, List<Parameter> parameters) {
        this.accessModifier = Objects.requireNonNull(accessModifier, "The accessModifier must not be null");
        this.returnType = Objects.requireNonNull(returnType, "The returnType must not be null");
        this.name = Objects.requireNonNull(name, "The name must not be null");
        this.parameters = Objects.requireNonNull(parameters, "The parameters must not be null");
    }

    static MyMethod from(ExecutableElement method) {
        if (method.getKind() != ElementKind.METHOD) {
            throw new IllegalArgumentException("The element must be method kind: " + method);
        }
        String accessModifier;
        {
            if (method.getModifiers().contains(Modifier.PUBLIC)) {
                accessModifier = "public";
            } else if (method.getModifiers().contains(Modifier.PROTECTED)) {
                accessModifier = "protected";
            } else {
                accessModifier = "";
            }
        }
        String returnType = method.getReturnType().getKind() == TypeKind.VOID
                ? "void"
                : method.getReturnType().toString();
        String name = method.getSimpleName().toString();
        List<Parameter> parameters = method.getParameters().stream()
                .map(p -> new Parameter(p.asType().toString(), p.getSimpleName().toString()))
                .collect(Collectors.toList());
        return new MyMethod(accessModifier, returnType, name, parameters);
    }

    String getAccessModifier() {
        return accessModifier;
    }

    String getReturnType() {
        return returnType;
    }

    String getName() {
        return name;
    }

    int getNumberOfParameters() {
        return parameters.size();
    }

    String getParametersLine() {
        return parameters.stream()
                .map(Parameter::toString)
                .collect(Collectors.joining(", "));
    }

    String getArgumentsLine() {
        return parameters.stream()
                .map(Parameter::name)
                .collect(Collectors.joining(", "));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyMethod myMethod = (MyMethod) o;
        return accessModifier.equals(myMethod.accessModifier)
                && returnType.equals(myMethod.returnType)
                && name.equals(myMethod.name)
                && parameters.equals(myMethod.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accessModifier, returnType, name, parameters);
    }

    static class Parameter {

        private final String type;
        private final String name;

        Parameter(String type, String name) {
            this.type = type;
            this.name = name;
        }

        String type() {
            return type;
        }

        String name() {
            return name;
        }

        @Override
        public String toString() {
            return type + " " + name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Parameter parameter = (Parameter) o;
            return type.equals(parameter.type) && name.equals(parameter.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(type, name);
        }

    }

}
