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

import dev.alexengrig.util.annotation.UnmodifiableWrapper;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.Collections;
import java.util.Set;

public final class UnmodifiableWrapperProcessor extends AbstractProcessor {

    private static final Class<UnmodifiableWrapper> ANNOTATION_CLASS = UnmodifiableWrapper.class;

    private final ContextFactory contextFactory;
    private final SourceGenerator sourceGenerator;

    public UnmodifiableWrapperProcessor() {
        this(new MyContextFactory(), new MySourceGenerator());
    }

    UnmodifiableWrapperProcessor(ContextFactory contextFactory, SourceGenerator sourceGenerator) {
        this.contextFactory = contextFactory;
        this.sourceGenerator = sourceGenerator;
    }

    private void process(TypeElement typeElement) {
        Context context = contextFactory.createContext(processingEnv, typeElement);
        String source = sourceGenerator.generate(context);
        JavaFileObject file = createFile(context);
        writeSourceToFile(source, file);
    }

    private JavaFileObject createFile(Context context) {
        String filename = context.getWrapperClassName();
        try {
            return processingEnv.getFiler().createSourceFile(filename);
        } catch (IOException e) {
            throw new UncheckedIOException("Exception of file creation: " + filename, e);
        }
    }

    private void writeSourceToFile(String source, JavaFileObject file) {
        try (Writer writer = file.openWriter()) {
            writer.write(source);
        } catch (IOException e) {
            throw new RuntimeException("Exception of file writing: " + file.getName(), e);
        }
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            return false;
        }
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(ANNOTATION_CLASS)) {
            process((TypeElement) annotatedElement);
        }
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(ANNOTATION_CLASS.getName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedOptions() {
        return Collections.emptySet();
    }

}
