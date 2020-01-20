# Xposed KTX

[ ![Download](https://api.bintray.com/packages/coxylicacid-official/xposed-ktx/xposed-ktx/images/download.svg?version=1.0.1) ](https://bintray.com/coxylicacid-official/xposed-ktx/xposed-ktx/1.0.1/link)
![](https://img.shields.io/badge/LATEST-1.0.1-brightgrees.svg)
[![](https://img.shields.io/badge/LICENSE-Apache2.0-ffc100.svg)](https://github.com/rhprincess/XposedKTX/blob/master/LICENSE)

#### A simple way to use your XposedBridge's api in Kotlin

### · Installation

1. First, you need to implement XposedBridge's api in your `build.gradle` file

   PS: You need to use `compileOnly` instead `implementation`

   ```gradle
   dependencies {
       // ...
       compileOnly 'de.robv.android.xposed:api:82'
       // ...
   }
   ```

2. Second, implement our library to your dependencies

   ```gradle
   dependencies {
       // ...
       compileOnly 'de.robv.android.xposed:api:82'
       implementation 'io.rhprincess.xp:xposed-ktx:$latest'
       // ...
   }
   ```



### · Usage

Not Finished,

To be continued.
