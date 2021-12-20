# unmodifiable-wrapper

[![Build Status](https://app.travis-ci.com/alexengrig/unmodifiable-wrapper.svg?branch=master)](https://app.travis-ci.com/alexengrig/unmodifiable-wrapper)

Like
[Collections#unmodifiableCollection](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#unmodifiableCollection-java.util.Collection-),
but for objects.

Unmodifiable-wrapper is an annotation processor for generating an unmodifiable wrapper.

The unmodifiable wrapper overrides all setter-methods - throws `UnsupportedOperationException`.

Required:

- Class must be not-abstract
- Class must be not-final.
- Class must be top-level.
- Class must have not-private no-arguments constructor.

## Get started

### Install

#### Gradle

Add this code to `dependencies` section in your `build.gradle`:

```groovy
compileOnly 'dev.alexengrig:metter:0.1.1'
annotationProcessor 'dev.alexengrig:metter:0.1.1'
```

### Example

Add `@UnmodifiableWrapper` to your class:

```java

@UnmodifiableWrapper
// not-abstract and not-final
class Domain {
    // fields

    // factory-method
    static Domain unmodifiable(Domain domain) {
        // generated class
        return new UnmodifiableDomain(domain);
    }

    // not-private no-args constructor
    Domain() {
    }

    // getters and setters
}
```

Usage:

```java
class Main {
    public static void main(String[] args) {
        Domain domain = ...
        Domain unmodifiableDomain = Domain.unmodifiable(domain);
        mutate(unmodifiableDomain);
    }

    static void mutate(Domain domain) {
        domain.setXXX(...)// throw UnsupportedOperationException
    }
}
```

## License

This project is [licensed](LICENSE) under [Apache License, version 2.0](https://www.apache.org/licenses/LICENSE-2.0).
