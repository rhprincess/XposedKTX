# Xposed KTX

#### A simple way to use your XposedBridge's api in Kotlin

### · Installation

1. First, you need to implement XposedBridge's api in your `build.gradle` file

   PS: You need to use `compileOnly` instead `implementation`

   ```groovy
   dependencies {
       // ...
       compileOnly 'de.robv.android.xposed:api:82'
       // ...
   }
   ```

2. Second, implement our library to your dependencies

   ```groovy
   dependencies {
       // ...
       compileOnly 'de.robv.android.xposed:api:82'
       implementation 'io.rhprincess.xp:xposed-ktx:1.0.0'
       // ...
   }
   ```



### · Usage

Not Finished,

To be continued.
