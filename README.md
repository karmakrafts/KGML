# 🧮 KGML

[![](https://git.karmakrafts.dev/kk/kgml/badges/master/pipeline.svg)](https://git.karmakrafts.dev/kk/kgml/-/pipelines)
[![](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Frepo.maven.apache.org%2Fmaven2%2Fdev%2Fkarmakrafts%2Fkgml%2Fkgml-core%2Fmaven-metadata.xml
)](https://git.karmakrafts.dev/kk/kgml/-/packages)
[![](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fcentral.sonatype.com%2Frepository%2Fmaven-snapshots%2Fdev%2Fkarmakrafts%2Fkgml%2Fkgml-core%2Fmaven-metadata.xml
)](https://git.karmakrafts.dev/kk/kgml/-/packages)
[![](https://img.shields.io/badge/2.4.0-blue?logo=kotlin&label=kotlin)](https://kotlinlang.org/)
[![](https://img.shields.io/badge/documentation-black?logo=kotlin)](https://docs.karmakrafts.dev/kgml-core)

The Kotlin Graphics Maths Library for Kotlin Multiplatform.  
A GLM/JOML inspired library with a touch of idiomatic Kotlin.

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
                implementation("dev.karmakrafts.kompress:kgml-core:<version>")
            }
        }
    }
}
```

Or, if you are only using Kotlin/JVM, add it to your top-level dependencies block instead.
