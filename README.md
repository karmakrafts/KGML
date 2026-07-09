# 🧮 KGML

[![](https://git.karmakrafts.dev/kk/kgml/badges/master/pipeline.svg)](https://git.karmakrafts.dev/kk/kgml/-/pipelines)
[![](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Frepo.maven.apache.org%2Fmaven2%2Fdev%2Fkarmakrafts%2Fkgml%2Fkgml-core%2Fmaven-metadata.xml
)](https://git.karmakrafts.dev/kk/kgml/-/packages)
[![](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fcentral.sonatype.com%2Frepository%2Fmaven-snapshots%2Fdev%2Fkarmakrafts%2Fkgml%2Fkgml-core%2Fmaven-metadata.xml
)](https://git.karmakrafts.dev/kk/kgml/-/packages)
[![](https://img.shields.io/badge/2.4.0-blue?logo=kotlin&label=kotlin)](https://kotlinlang.org/)
[![](https://img.shields.io/badge/documentation-black?logo=kotlin)](https://docs.karmakrafts.dev/kgml-core)

![](https://img.shields.io/badge/-JVM-blue?logo=kotlin&labelColor=black)
![](https://img.shields.io/badge/-Android-green?logo=kotlin&labelColor=black)
![](https://img.shields.io/badge/-Native-lightgray?logo=kotlin&labelColor=black)
![](https://img.shields.io/badge/-JS-gold?logo=kotlin&labelColor=black)
![](https://img.shields.io/badge/-WASM/JS-orange?logo=kotlin&labelColor=black)
![](https://img.shields.io/badge/-WASM/WASI-purple?logo=kotlin&labelColor=black)

The Kotlin Graphics Maths Library for Kotlin Multiplatform.  
A GLM/JOML inspired library with a touch of idiomatic Kotlin.

### Features

- 2, 3 and 4-dimensional float vector types
- 2, 3 and 4-dimensional double vector types
- 3, 4 and 4-dimensional integer vector types
- 2x2, 3x3 and 4x4 float matrices
- 2x2, 3x3 and 4x4 double matrices
- Matrix-vector-multiplication
- Orthographic and perspective projection
- Matrix stacks
- Quaternions
- Composition based transformation API
- Hardware acceleration on JVM and native using FMA
- [kotlinx.io](https://github.com/Kotlin/kotlinx-io) read- and write-extensions for all KGML types via `kgml-io`

### How to use it

First, add the official Maven Central repository to your settings.gradle.kts:

```kotlin
dependencyResolutionManagement {
    repositories {
        maven("https://central.sonatype.com/repository/maven-snapshots")
        mavenCentral()
    }
}
```

Then add a dependency on the library in your root buildscript:

```kotlin
kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation("dev.karmakrafts.kgml:kgml-core:<version>")
            }
        }
    }
}
```

Or, if you are only using Kotlin/JVM, add it to your top-level dependencies block instead.
