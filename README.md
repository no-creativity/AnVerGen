# AnVerGen - Android Version Generator

[![Travis](https://travis-ci.org/no-creativity/AnVerGen.svg?branch=master)](https://travis-ci.org/no-creativity/AnVerGen)
[![AppVeyor](https://ci.appveyor.com/api/projects/status/91b78sqeuro6abg8?svg=true)](https://ci.appveyor.com/project/yanqd0/anvergen)
[![Releases](https://img.shields.io/github/release/no-creativity/AnVerGen.svg)](https://github.com/no-creativity/AnVerGen/releases/latest)
[![Bintray](https://api.bintray.com/packages/no-creativity/maven/AnVerGen/images/download.svg)](https://bintray.com/no-creativity/maven/AnVerGen/_latestVersion)
[![JitPack](https://jitpack.io/v/no-creativity/AnVerGen.svg)](https://jitpack.io/#no-creativity/AnVerGen)
[![CodeCov](https://codecov.io/gh/no-creativity/AnVerGen/branch/master/graph/badge.svg)](https://codecov.io/gh/no-creativity/AnVerGen)
[![VersionEye](https://www.versioneye.com/user/projects/580c87b9912815139a3d0520/badge.svg)](https://www.versioneye.com/user/projects/580c87b9912815139a3d0520)

An automatic version generator for Android applications, implemented by Groovy.

## Usage

It is a git based version generator, and git tags, especially annotated and signed tags, are the most important references.

### Generate with git

The version code will be the commit count of git, and the version name will be a result of `git describe`.

- If the current commit has a tag on it, then the version name is the tag.
- If the current commit has no tag on it, then the version name is `$tag-$count-g$sha1_7`.
    + `$tag` is the previous tag name along the branch.
    + `$count` is the commit count from the previous tag to current.
    + `g` means the version control system is Git.
    + `$sha1_7` means the first 7 characters of the current SHA1.
    + It's the original `git describe` result above.
- If there is no tag in the repository, then assume the tag is `0.0.0`. Others are the same as above.

Finally, the version name is something like `0.5.0` at the tag `0.5.0`, and `0.4.0-8-g8292719` at the commit `0.5.0^`.

As you see, the tag name of your git repository is recommended to be something like `0.1.0`, `1.0.0`.
It's strongly recommended to read [Semantic Versioning](http://semver.org/) for help.

### Add to dependency

It's a version generator used in the **gradle build script**.

It's not a dependency of an application, but a dependency of a gradle build script, so there is a little difference from other JARs.

Modify the `build.gradle` in the root directory. You can get this JAR from Bintray:

```groovy
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'org.no_creativity:AnVerGen:0.7.0'
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
        classpath 'com.github.no-creativity:AnVerGen:0.7.0'
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

You can read the [groovydoc](https://jitpack.io/com/github/no-creativity/AnVerGen/0.7.0/javadoc/) for more usage.

## Change request

If there is any new demand or problem of this JAR, please give me an [issue](https://github.com/no-creativity/AnVerGen/issues).

Or you can fork and distribute it.

## License

>  Copyright 2016 Yan QiDong
>
>  Licensed under the Apache License, Version 2.0
>
>  See: <http://www.apache.org/licenses/LICENSE-2.0>
