package io.rhprincess.xposed_ktx

import android.content.res.XModuleResources
import android.view.View
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_InitPackageResources
import de.robv.android.xposed.callbacks.XC_LayoutInflated
import io.rhprincess.xposed_ktx.impl.XposedLayoutInflatedImpl
import io.rhprincess.xposed_ktx.impl.XposedMethodImpl

/**
 * The hook proxy 4 String, and turn it into Class
 */
class HookerProxyForString(internal var className: String) :
    XposedMethodImpl {
    var classLoader: ClassLoader? = null
    var methodName: String = ""
    var parameterTypes: Any? = null
    internal var afterHook: (XC_MethodHook.MethodHookParam) -> Unit = { }
    internal var beforeHook: (XC_MethodHook.MethodHookParam) -> Unit = { }

    override fun before(before: (XC_MethodHook.MethodHookParam) -> Unit) {
        beforeHook = before
    }

    override fun after(after: (XC_MethodHook.MethodHookParam) -> Unit) {
        afterHook = after
    }
}

class ConstructorHookerProxyForString(internal var className: String) :
    XposedMethodImpl {
    var classLoader: ClassLoader? = null
    var parameterTypes: Any? = null
    internal var afterHook: (XC_MethodHook.MethodHookParam) -> Unit = { }
    internal var beforeHook: (XC_MethodHook.MethodHookParam) -> Unit = { }

    override fun before(before: (XC_MethodHook.MethodHookParam) -> Unit) {
        beforeHook = before
    }

    override fun after(after: (XC_MethodHook.MethodHookParam) -> Unit) {
        afterHook = after
    }
}

/**
 * The hook proxy 4 Class
 */
class HookerProxyForClazz(internal var clazz: Class<*>) :
    XposedMethodImpl {
    var methodName: String = ""
    var parameterTypes: Any? = null
    internal var afterHook: (param: XC_MethodHook.MethodHookParam) -> Unit = { }
    internal var beforeHook: (param: XC_MethodHook.MethodHookParam) -> Unit = { }

    override fun before(before: (XC_MethodHook.MethodHookParam) -> Unit) {
        beforeHook = before
    }

    override fun after(after: (XC_MethodHook.MethodHookParam) -> Unit) {
        afterHook = after
    }
}

class ConstructorHookerProxyForClazz(internal var clazz: Class<*>) :
    XposedMethodImpl {
    var parameterTypes: Any? = null
    internal var afterHook: (param: XC_MethodHook.MethodHookParam) -> Unit = { }
    internal var beforeHook: (param: XC_MethodHook.MethodHookParam) -> Unit = { }

    override fun before(before: (XC_MethodHook.MethodHookParam) -> Unit) {
        beforeHook = before
    }

    override fun after(after: (XC_MethodHook.MethodHookParam) -> Unit) {
        afterHook = after
    }
}

/**
 * this class is 4 replacing resources
 */
class ReplaceProxy() {
    var pkgName: String = ""
    var type: String = ""
    var name: String = ""
    var replacement: Any? = null

    //Primary Constructor
    constructor(pkgName: String) : this() {
        this.pkgName = pkgName
    }

    //Secondary Constructor
    constructor(pkgName: String, type: String, name: String, replacement: Any) : this() {
        this.pkgName = pkgName
        this.type = type
        this.name = name
        this.replacement = replacement
    }
}

/**
 * this is the class to proxy Xposed's hookLayout method
 */
class LayoutHookerProxy(internal var pkgName: String) :
    XposedLayoutInflatedImpl {
    var type: String = ""
    var name: String = ""
    var resp: XC_InitPackageResources.InitPackageResourcesParam? = null
    var modRes: XModuleResources? = null
    internal var response: (liparam: XC_LayoutInflated.LayoutInflatedParam) -> Unit = { }

    override fun layoutLoaded(liparam: (XC_LayoutInflated.LayoutInflatedParam) -> Unit) {
        response = liparam
    }

    /**
     * A method that can make you do some replacement in resource more easier
     */
    fun replace(init: ReplaceProxy.() -> Unit) {
        val wrap = ReplaceProxy(pkgName)
        wrap.init()
        resp!!.res.setReplacement(wrap.pkgName, wrap.type, wrap.name, wrap.replacement)

    }

    fun replace(p: List<ReplaceProxy>) {
        for (proxy in p) {
            XposedKTX.log(
                XposedKTX.REPLACE_TAG,
                "replace ${proxy.pkgName}://${proxy.type}/${proxy.name}"
            )
            resp!!.res.setReplacement(proxy.pkgName, proxy.type, proxy.pkgName, proxy.replacement)
        }
    }
}

private fun startHook(wrap: HookerProxyForString): XC_MethodHook.Unhook {
    return XposedHelpers.findAndHookMethod(
        wrap.className,
        wrap.classLoader,
        wrap.methodName,
        wrap.parameterTypes,
        object : XC_MethodHook() {
            @Throws(Throwable::class)
            override fun beforeHookedMethod(param: MethodHookParam) {
                wrap.beforeHook(param)
            }

            @Throws(Throwable::class)
            override fun afterHookedMethod(param: MethodHookParam) {
                wrap.afterHook(param)
            }
        })
}

private fun startHook(wrap: HookerProxyForClazz): XC_MethodHook.Unhook {
    return XposedHelpers.findAndHookMethod(
        wrap.clazz,
        wrap.methodName,
        wrap.parameterTypes,
        object : XC_MethodHook() {
            @Throws(Throwable::class)
            override fun beforeHookedMethod(param: MethodHookParam) {
                wrap.beforeHook(param)
            }

            @Throws(Throwable::class)
            override fun afterHookedMethod(param: MethodHookParam) {
                wrap.afterHook(param)
            }
        })
}

private fun startConstructorHook(wrap: ConstructorHookerProxyForString): XC_MethodHook.Unhook {
    return XposedHelpers.findAndHookConstructor(
        wrap.className,
        wrap.classLoader,
        wrap.parameterTypes,
        object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam) {
                wrap.beforeHook(param)
            }

            @Throws(Throwable::class)
            override fun afterHookedMethod(param: MethodHookParam) {
                wrap.afterHook(param)
            }
        })
}

private fun startConstructorHook(wrap: ConstructorHookerProxyForClazz): XC_MethodHook.Unhook {
    return XposedHelpers.findAndHookConstructor(
        wrap.clazz,
        wrap.parameterTypes,
        object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam) {
                wrap.beforeHook(param)
            }

            @Throws(Throwable::class)
            override fun afterHookedMethod(param: MethodHookParam) {
                wrap.afterHook(param)
            }
        })
}

private fun startLayoutHook(wrap: LayoutHookerProxy): XC_LayoutInflated.Unhook {
    return wrap.resp!!.res.hookLayout(
        wrap.pkgName,
        wrap.type,
        wrap.name,
        object : XC_LayoutInflated() {
            override fun handleLayoutInflated(liparam: LayoutInflatedParam?) {
                wrap.response(liparam!!)
            }
        })
}

/**
 * Extension for string to hook a class's method
 */
fun String.hook(init: HookerProxyForString.() -> Unit): XC_MethodHook.Unhook {
    val wrap = HookerProxyForString(this)
    wrap.init()
    return startHook(wrap)
}

/**
 * Extension for class to hook it's method
 */
fun Class<*>.hook(init: HookerProxyForClazz.() -> Unit): XC_MethodHook.Unhook {
    val wrap = HookerProxyForClazz(this)
    wrap.init()
    return startHook(wrap)
}

/**
 * Extension for string to hook a class's constructor
 */
fun String.hookCnstructor(init: ConstructorHookerProxyForString.() -> Unit): XC_MethodHook.Unhook {
    val wrap = ConstructorHookerProxyForString(this)
    wrap.init()
    return startConstructorHook(wrap)
}

/**
 * Extension for class to hook it's constructor
 */
fun Class<*>.hookCnstructor(init: ConstructorHookerProxyForClazz.() -> Unit): XC_MethodHook.Unhook {
    val wrap = ConstructorHookerProxyForClazz(this)
    wrap.init()
    return startConstructorHook(wrap)
}

/**
 * Extension for string to hook resources
 */
fun String.resHook(init: LayoutHookerProxy.() -> Unit): XC_LayoutInflated.Unhook {
    val wrap = LayoutHookerProxy(this)
    wrap.init()
    return startLayoutHook(wrap)
}

/**
 * Extension for finding view in LayoutInflatedParam
 * @see XC_LayoutInflated.LayoutInflatedParam
 */
fun XC_LayoutInflated.LayoutInflatedParam.findViewById(id: String): View {
    val res = this.res
    val idI = res.getIdentifier(id, "id", res.packageName)
    return this.view.findViewById(idI)
}

/* -----------------------    4 resource's replacements    ---------------------------------- */
fun XC_InitPackageResources.InitPackageResourcesParam.replace(init: ReplaceProxy.() -> Unit) {
    val wrap = ReplaceProxy(this.packageName)
    wrap.init()
    this.res.setReplacement(wrap.pkgName, wrap.type, wrap.name, wrap.replacement)
}

fun XC_InitPackageResources.InitPackageResourcesParam.replace(
    type: String,
    name: String,
    replacement: Any
) {
    this.res.setReplacement(this.packageName, type, name, replacement)
}

fun XC_InitPackageResources.InitPackageResourcesParam.replace(
    pkgName: String,
    type: String,
    name: String,
    replacement: Any
) {
    this.res.setReplacement(pkgName, type, name, replacement)
}

fun XC_InitPackageResources.InitPackageResourcesParam.replace(p: List<ReplaceProxy>) {
    for (proxy in p) {
        XposedKTX.log(
            XposedKTX.REPLACE_TAG,
            "replace ${proxy.pkgName}://${proxy.type}/${proxy.name}"
        )
        this.res.setReplacement(proxy.pkgName, proxy.type, proxy.name, proxy.replacement)
    }
}
/* ------------------------------------------------------------------------------------------ */