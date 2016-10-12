# AnVerGen - Android Version Generator

[![Build Status](https://travis-ci.org/no-creativity/AnVerGen.svg?branch=master)](https://travis-ci.org/no-creativity/AnVerGen)
[![Download](https://api.bintray.com/packages/no-creativity/maven/AnVerGen/images/download.svg)](https://bintray.com/no-creativity/maven/AnVerGen/_latestVersion)
[![JitPack](https://jitpack.io/v/no-creativity/AnVerGen.svg)](https://jitpack.io/#no-creativity/AnVerGen)

An automatic version generator for Android applications, implemented by Groovy.

## Usage

### Add to dependency

It's a version generator used in the gradle build script.
It's not a dependency of an application, but a dependency of a gradle build script, so there is a little difference from other JARs.

Modify the `build.gradle` in the root directory. You can get this JAR from Bintray:

```groovy
buildscript {
    repositories {
        jcenter()
        // If the JCenter do not accepts this project.
        maven { url "https://dl.bintray.com/no-creativity/maven" }
    }
    dependencies {
        classpath 'org.no_creativity:AnVerGen:0.2.11'
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
        classpath 'com.github.no-creativity:AnVerGen:0.2'
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

## License

>  Copyright 2016 Yan QiDong
>
>  Licensed under the Apache License, Version 2.0 (the "License");
>
>  you may not use this file except in compliance with the License.
>
>  You may obtain a copy of the License at
>
>      http://www.apache.org/licenses/LICENSE-2.0
>
>  Unless required by applicable law or agreed to in writing, software
>
>  distributed under the License is distributed on an "AS IS" BASIS,
>
>  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
>
>  See the License for the specific language governing permissions and
>
>  limitations under the License.
