# Xposed KTX

[![Download](https://api.bintray.com/packages/coxylicacid-official/xposed-ktx/xposed-ktx/images/download.svg?version=1.0.1)](https://bintray.com/coxylicacid-official/xposed-ktx/xposed-ktx/1.0.1/link)
![](https://img.shields.io/badge/Latest-1.0.1-brightgrees.svg)
[![](https://img.shields.io/badge/License-Apache%202.0-ffc100.svg)](https://github.com/rhprincess/XposedKTX/blob/master/LICENSE)

#### A simple way to use your XposedBridge's api in Kotlin

### · Installation

First, you need to implement XposedBridge's api in your `build.gradle` file
PS: You need to use `compileOnly` instead `implementation`
Second, implement our library to your dependencies

   ```gradle
   dependencies {
       // ...
       compileOnly 'de.robv.android.xposed:api:82'
       implementation 'io.rhprincess.xp:xposed-ktx:$latest'
       // ...
   }
   ```

### · Current API 

| Method | Description |  Param | Made Available For |
| --- | --- | --- | --- |
|  String.hook  |  Use class name string as our hook aim  |  init: HookerProxyForString.() -> Unit  |  String |
|  Class<*>.hook  |  Directly use class to hook its method |  init: HookerProxyForClazz.() -> Unit  |  Class<*>  |
|  String.hookConstructor |  Use class name string to hook a class’s constructor  |  init: ConstructorHookerProxyForString.() -> Unit  |  String  |
|  Class<*>.hookConstructor |  Directly use class to hook its constructor |  init: ConstructorHookerProxyForClazz.() -> Unit  |  Class<*>  |
|  String.resHook |  Hook a package’s resources with its package name string |  init: LayoutHookerProxy.() -> Unit  |  String  |
|  LayoutInflatedParam.findViewById  |  Extension for LayoutInflatedParam using an id string to find a view instance |  id: String  |  XC_LayoutInflated.LayoutInflatedParam  |
|  InitPackageResourcesParam.replace  |  replace a resource |  type: String, name: String, replacement: Any  |  XC_InitPackageResources.InitPackageResourcesParam  |
|  InitPackageResourcesParam.replace  |  replace a resource  |  pkgName: String, type: String, name: String, replacement: Any  |  XC_InitPackageResources.InitPackageResourcesParam  |
|  InitPackageResourcesParam.replace  |  replace a resource  |  p: List< ReplaceProxy >  |  XC_InitPackageResources.InitPackageResourcesParam  |
|  InitPackageResourcesParam.replace  |  replace a resource  |  init: ReplaceProxy.() -> Unit |  XC_InitPackageResources.InitPackageResourcesParam  |

### Examples 

1. Hooking an activity’s `onCreate` method.

```kotlin 
val activity = "xxx.xxx.xxx.MainActivity"
activity.hook {
    classLoader = lpparam.classLoader // Essential
    methodName = "onCreate" // Essential
    parameterTypes = Bundle::class.java // Non-Essential
    after {
        Log.e("Xposed KTX", "I'm successfully been hooked")
        val activity = it.thisObject as Activity
        Toast.makeText(activity, "You had been slain", Toast.LENGTH_LONG).show()
    }
    before {
        // If you don't need this method, you can omit it
    }
}
```

You can hook method using Class as well.

```kotlin
MainActivity::class.java.hook {
    methodName = "onCreate" // Essential
    parameterTypes = Bundle::class.java // Non-Essential
    after {
        Log.e("Xposed KTX", "I'm successfully been hooked")
        val activity = it.thisObject as Activity
        Toast.makeText(activity, "You had been slain", Toast.LENGTH_LONG).show()
    }
    before {
        // If you don't need this method, you can omit it
    }
}
```

2. Hooking resources.

   ```kotlin
   private var MODULE_PATH = ""
   
   override fun initZygote(startupParam: IXposedHookZygoteInit.StartupParam?) {
       MODULE_PATH = startupParam!!.modulePath
   }
   
   override fun handleInitPackageResources(resparam: XC_InitPackageResources.InitPackageResourcesParam?) {
       "com.android.systemui".resHook {
           type = "layout"
           name = "activity_main"
           resp = resparam
           modRes = XModuleResources.createInstance(MODULE_PATH, resparam.res) // create module resource
           replace { type = "drawable"; name = "ic_chart"; replacement = modRes!!.fwd(R.drawable.ic_xp) } //replace a image resource
           layoutLoaded { // handleLayoutInflated
               val clock = it.findViewById("clock") as TextView
               clock.setTextColor(Color.RED);
           }
       }
   }
   ```
