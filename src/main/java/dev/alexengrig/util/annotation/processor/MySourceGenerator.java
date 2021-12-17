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

import java.util.StringJoiner;

class MySourceGenerator implements SourceGenerator {

    @Override
    public String generate(Context context) {
        StringJoiner joiner = new StringJoiner("\n");
        context.getPackageName().ifPresent(packageName -> joiner.add("package " + packageName + ";\n"));
        String className = context.getWrapperSimpleClassName();
        String parentClassName = context.getDomainClassName();
        String classAccessModifier = context.isWrapperClassPublic() ? "public" : "";
        joiner.add(classAccessModifier + " class " + className + " extends " + parentClassName + " {");
        joiner.add("");
        joiner.add("    private final " + parentClassName + " delegate;");
        joiner.add("");
        joiner.add("    " + classAccessModifier + " " + className + "(" + parentClassName + " delegate) {");
        joiner.add("        this.delegate = java.util.Objects.requireNonNull(delegate, \"The delegate must not be null\");");
        joiner.add("    }");
        joiner.add("");
        joiner.add("    // Modification methods");
        joiner.add("");
        for (MyMethod method : context.getModificationMethods()) {
            joiner.add("    @java.lang.Override");
            String accessModifier = method.getAccessModifier();
            String returnType = method.getReturnType();
            String name = method.getName();
            String parameters = method.getParametersLine();
            joiner.add("    " + accessModifier + " " + returnType + " " + name + "(" + parameters + ") {");
            joiner.add("        throw new UnsupportedOperationException();");
            joiner.add("    }");
            joiner.add("");
        }
        joiner.add("    // Other methods");
        joiner.add("");
        for (MyMethod method : context.getOtherMethods()) {
            joiner.add("    @java.lang.Override");
            String accessModifier = method.getAccessModifier();
            String returnType = method.getReturnType();
            String name = method.getName();
            String parameters = method.getParametersLine();
            joiner.add("    " + accessModifier + " " + returnType + " " + name + "(" + parameters + ") {");
            String callPrefix = "void".equals(returnType) ? "" : "return ";
            String arguments = method.getArgumentsLine();
            joiner.add("        " + callPrefix + "delegate." + name + "(" + arguments + ");");
            joiner.add("    }");
            joiner.add("");
        }
        joiner.add("    // Object methods");
        joiner.add("");
        joiner.add("    @java.lang.Override");
        joiner.add("    public boolean equals(Object o) {");
        joiner.add("        if (this == o) return true;");
        joiner.add("        if (o == null || getClass() != o.getClass()) return false;");
        joiner.add("        " + className + " that = (" + className + ") o;");
        joiner.add("        return delegate.equals(that.delegate);");
        joiner.add("    }");
        joiner.add("");
        joiner.add("    @java.lang.Override");
        joiner.add("    public int hashCode() {");
        joiner.add("        return delegate.hashCode();");
        joiner.add("    }");
        joiner.add("");
        joiner.add("    @java.lang.Override");
        joiner.add("    public String toString() {");
        joiner.add("        return \"" + className + "{\" + delegate + \"}\";");
        joiner.add("    }");
        joiner.add("");
        joiner.add("}");
        joiner.add("");
        return joiner.toString();
    }

}
