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

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.util.Optional;
import java.util.Set;

class MyContext implements Context {

    private final ContextHelper helper;
    private final TypeElement domainTypeElement;

    private String domainSimpleClassName;
    private String wrapperSimpleClassName;

    private String domainClassName;
    private String wrapperClassName;

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private Optional<String> packageNameOptional;

    private Set<MyMethod> allMethods;
    private Set<MyMethod> modificationMethods;
    private Set<MyMethod> otherMethods;

    MyContext(ContextHelper helper, TypeElement type) {
        this.helper = helper;
        this.domainTypeElement = type;
    }

    @Override
    public TypeElement getType() {
        return domainTypeElement;
    }

    @Override
    @SuppressWarnings("OptionalAssignedToNull")
    public Optional<String> getPackageName() {
        if (packageNameOptional == null) {
            packageNameOptional = helper.getPackageName(this);
        }
        return packageNameOptional;
    }

    @Override
    public boolean isWrapperClassPublic() {
        return domainTypeElement.getModifiers().contains(Modifier.PUBLIC);
    }

    @Override
    public String getDomainSimpleClassName() {
        if (domainSimpleClassName == null) {
            domainSimpleClassName = helper.getDomainSimpleClassName(this);
        }
        return domainSimpleClassName;
    }

    @Override
    public String getWrapperSimpleClassName() {
        if (wrapperSimpleClassName == null) {
            wrapperSimpleClassName = helper.getWrapperSimpleClassName(this);
        }
        return wrapperSimpleClassName;
    }

    @Override
    public String getDomainClassName() {
        if (domainClassName == null) {
            domainClassName = helper.getDomainClassName(this);
        }
        return domainClassName;
    }

    @Override
    public String getWrapperClassName() {
        if (wrapperClassName == null) {
            wrapperClassName = helper.getWrapperClassName(this);
        }
        return wrapperClassName;
    }

    @Override
    public Set<MyMethod> getAllOverridableMethods() {
        if (allMethods == null) {
            allMethods = helper.getAllOverridableMethods(this);
        }
        return allMethods;

    }

    @Override
    public Set<MyMethod> getModificationMethods() {
        if (modificationMethods == null) {
            modificationMethods = helper.getModificationMethods(this);
        }
        return modificationMethods;
    }

    @Override
    public Set<MyMethod> getOtherMethods() {
        if (otherMethods == null) {
            otherMethods = helper.getOtherMethods(this);
        }
        return otherMethods;
    }

}
