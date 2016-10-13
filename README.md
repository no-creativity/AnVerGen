# AnVerGen - Android Version Generator

[![Build Status](https://travis-ci.org/no-creativity/AnVerGen.svg?branch=master)](https://travis-ci.org/no-creativity/AnVerGen)
[![Download](https://api.bintray.com/packages/no-creativity/maven/AnVerGen/images/download.svg)](https://bintray.com/no-creativity/maven/AnVerGen/_latestVersion)
[![JitPack](https://jitpack.io/v/no-creativity/AnVerGen.svg)](https://jitpack.io/#no-creativity/AnVerGen)
[![codecov](https://codecov.io/gh/no-creativity/AnVerGen/branch/master/graph/badge.svg)](https://codecov.io/gh/no-creativity/AnVerGen)

An automatic version generator for Android applications, implemented by Groovy.

## Usage

It is a git based version generator, and git tags are the most important reference.

### Generate with git

The version code will be the commit count of git, and the version name will be a specific string.

```groovy
    "$version.$subVersion.$date.$shortSha1"
```

- `version` is the latest `git tag`. It is recommended to name the git tag like 0.1, 1.0 and so on.
- `subVersion` is the commit count from last `git tag`. If the current git head is the tag, then the count is 0.
- `date` is the date of the compilation, formatted as `yyMMdd`.
- `shortSha1` is a substring of SHA1 of current git object.

Finally, the version name is something like `0.2.14.161013.fcc3088`. `0.2` is the latest git tag. `14` is the commit count from tag `0.2`.

### Add to dependency

It's a version generator used in the gradle build script.
It's not a dependency of an application, but a dependency of a gradle build script, so there is a little difference from other JARs.

Modify the `build.gradle` in the root directory. You can get this JAR from Bintray:

```groovy
buildscript {
    repositories {
        jcenter()
        // This is the private maven repository below.
        // maven { url "https://dl.bintray.com/no-creativity/maven" }
    }
    dependencies {
        classpath 'org.no_creativity:AnVerGen:0.3.0'
    }
}
```

Or JitPack:

```groovy
buildscript {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
    dependencies {
        classpath 'com.github.no-creativity:AnVerGen:0.3'
    }
}
```

You can see the latest dependency snippet by clicking the badges **Download** or **JitPack**.

### Invoke in the build script

```groovy
import org.no_creativity.anvergen.Ver

android {
    defaultConfig {
        versionCode Ver.generateVersionCode()
        versionName Ver.generateVersionName()
    }
}
```

## Change request

If there is any new demand or problem of this JAR, please give me an [issue](https://github.com/no-creativity/AnVerGen/issues).

Or you can fork and distribute it.

## License

>  Copyright 2016 Yan QiDong
>
>  Licensed under the Apache License, Version 2.0
>
>  See: <http://www.apache.org/licenses/LICENSE-2.0>
