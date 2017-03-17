# kOptional

[![Download](https://api.bintray.com/packages/step-89-g/stepango/kOptional/images/download.svg?version=1.1.0) ](https://bintray.com/step-89-g/stepango/kOptional/1.1.0/link)
[![codebeat badge](https://codebeat.co/badges/7a9c63ff-cae7-455b-bca6-8d68880c6907)](https://codebeat.co/projects/github-com-stepango-koptional)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-kOptional-brightgreen.svg?style=flat)]()

Extended Java8 `Optional` Kotlin back-port for Android and JVM

Add kOptional to your `build.gradle`
```
compile "com.stepango.koptional:koptional:1.1.0"
```

Usage example:
```
arrayOf(null, "1", null, "2", null, "3")
        .map { it.toOptional() }
        .mapIndexed { i, optional -> optional.ifEmpty { Log.e("kOptional", "$i element is null, it's bad") } }
        .map { it.map { "$it is not null " } }
        .forEach { it.ifPresent(::println) }

// 1 is not null 
// 2 is not null 
// 3 is not null

Log:

// 0 element is null, it's bad
// 2 element is null, it's bad
// 4 element is null, it's bad

```
