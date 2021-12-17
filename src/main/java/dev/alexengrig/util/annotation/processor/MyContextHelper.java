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

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class MyContextHelper implements ContextHelper {

    static final Pattern SETTER_METHOD_REGEX = Pattern.compile("set[A-Z].*");

    private final ProcessingEnvironment environment;

    public MyContextHelper(ProcessingEnvironment environment) {
        this.environment = environment;
    }

    static boolean isNotObjectMethod(MyMethod method) {
        switch (method.getName()) {
            case "equals":
                return !"public".equals(method.getAccessModifier())
                        || !"boolean".equals(method.getReturnType())
                        || 1 != method.getNumberOfParameters();
            case "hashCode":
                return !"public".equals(method.getAccessModifier())
                        || !"int".equals(method.getReturnType())
                        || 0 != method.getNumberOfParameters();
            case "toString":
                return !"public".equals(method.getAccessModifier())
                        || !"java.lang.String".equals(method.getReturnType())
                        || 0 != method.getNumberOfParameters();
            default:
                return true;
        }
    }

    static boolean isSetterMethod(MyMethod method) {
        boolean hasOnlyOneParameter = method.getNumberOfParameters() == 1;
        if (!hasOnlyOneParameter) {
            return false;
        }
        boolean isVoidReturnType = "void".equals(method.getReturnType());
        if (!isVoidReturnType) {
            return false;
        }
        return SETTER_METHOD_REGEX.matcher(method.getName()).matches();
    }

    @Override
    public String getDomainSimpleClassName(Context context) {
        return ElementUtil.getSimpleClassName(context.getType());
    }

    @Override
    public String getWrapperSimpleClassName(Context context) {
        return WRAPPER_CLASS_NAME_PREFIX.concat(context.getDomainSimpleClassName());
    }

    @Override
    public String getDomainClassName(Context context) {
        return ElementUtil.getClassName(context.getType());
    }

    @Override
    public String getWrapperClassName(Context context) {
        Optional<String> packageNameOptional = context.getPackageName();
        String simpleClassName = context.getWrapperSimpleClassName();
        return packageNameOptional
                .map(packageName -> packageName.concat(".").concat(simpleClassName))
                .orElse(simpleClassName);
    }

    @Override
    public Optional<String> getPackageName(Context context) {
        return ElementUtil.getPackageName(context.getType());
    }

    @Override
    public Set<MyMethod> getAllOverridableMethods(Context context) {
        return ElementUtil.getAllMethods(context.getType())
                .filter(m -> !m.getModifiers().contains(Modifier.PRIVATE)
                        && !m.getModifiers().contains(Modifier.STATIC)
                        && !m.getModifiers().contains(Modifier.FINAL))
                .map(MyMethod::from)
                .filter(MyContextHelper::isNotObjectMethod)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<MyMethod> getOtherMethods(Context context) {
        Set<MyMethod> modificationMethods = context.getModificationMethods();
        return context.getAllOverridableMethods().stream()
                .filter(Predicate.not(modificationMethods::contains))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<MyMethod> getModificationMethods(Context context) {
        return context.getAllOverridableMethods().stream()
                .filter(MyContextHelper::isSetterMethod)
                .collect(Collectors.toSet());
    }

}
