# kOptional

[ ![Download](https://api.bintray.com/packages/step-89-g/stepango/kOptional/images/download.svg?version=1.0.1) ](https://bintray.com/step-89-g/stepango/kOptional/1.0.1/link)
[![codebeat badge](https://codebeat.co/badges/7a9c63ff-cae7-455b-bca6-8d68880c6907)](https://codebeat.co/projects/github-com-stepango-koptional)
[![License](https://img.shields.io/:License-Apache 2.0-orange.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-kOptional-brightgreen.svg?style=flat)]()
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
