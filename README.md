## pngquant-android
[![Circle CI](https://circleci.com/gh/ndahlquist/pngquant-android.svg?style=svg)](https://circleci.com/gh/ndahlquist/pngquant-android)

[pngquant](https://pngquant.org/) with basic Java bindings for Android.
![pngquant](https://pngquant.org/pngquant-logo.png)

###Usage:
In your build.gradle:
```groovy
dependencies {
    compile 'com.ndahlquist:pngquant-android:0.2'
}

```

In your Android app:
```java
File inputPngFile = getYourPng();
File outputPngFile = getOutputFile()
new LibPngQuant().pngQuantFile(inputFile, outputFile);
```
