# kOptional

[ ![Download](https://api.bintray.com/packages/step-89-g/stepango/kOptional/images/download.svg?version=1.0.1) ](https://bintray.com/step-89-g/stepango/kOptional/1.0.1/link)

Java8 Optional back-port for Kotlin and Android

Add kOptional to your `build.gradle`
```
compile "com.stepango.koptional:koptional:1.0.1"
```

Usage example:
```
arrayOf(null, "1", null, "2", null, "3")
        .map { it.toOptional() }
        .map { it.map { "$it is not null " } }
        .forEach { it.ifPresent(::println) }

// 1 is not null 
// 2 is not null 
// 3 is not null 
```
