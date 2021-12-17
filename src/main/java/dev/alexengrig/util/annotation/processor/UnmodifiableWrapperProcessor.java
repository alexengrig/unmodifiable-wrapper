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
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.Collections;
import java.util.Optional;
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

    private void warning(String message, TypeElement type) {
        Optional<AnnotationMirror> optionalAnnotation = ElementUtil.getAnnotationMirror(ANNOTATION_CLASS, type);
        if (optionalAnnotation.isPresent()) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, message, type, optionalAnnotation.get());
        } else {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, message, type);
        }
    }

    private void note(String message) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);
    }

    private boolean isTargetType(TypeElement type) {
        if (type.getModifiers().contains(Modifier.FINAL)) {
            warning("Cannot inherit from final class.", type);
            return false;
        } else if (type.getModifiers().contains(Modifier.ABSTRACT)) {
            warning("Cannot work with abstract class.", type);
            return false;
        } else if (type.getNestingKind() != NestingKind.TOP_LEVEL) {
            warning("Cannot work with not top-level class.", type);
            return false;
        }
        return true;
    }

    private void process(TypeElement type) {
        Context context = contextFactory.createContext(processingEnv, type);
        String source = sourceGenerator.generate(context);
        JavaFileObject file = createFile(context);
        writeSourceToFile(source, file);
        note("Created unmodifiable wrapper - " + context.getWrapperClassName());
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
            TypeElement type = (TypeElement) annotatedElement;
            if (isTargetType(type)) {
                process(type);
            }
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
