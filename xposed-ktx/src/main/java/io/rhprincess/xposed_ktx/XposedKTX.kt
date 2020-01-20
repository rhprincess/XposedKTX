package io.rhprincess.xposed_ktx

import de.robv.android.xposed.XposedBridge

object XposedKTX {

    const val TAG = "XPOSED-KTX"
    const val REPLACE_TAG = "REPLACE-RESOURCE"
    const val LAYOUT_INFLATED = "LAYOUT-INFLATED"
    const val HOOK_METHOD_TAG = "HOOK-METHOD"
    const val HOOK_CONSTRUCTOR_TAG = "HOOK-CONSTRUCTOR"

    var isLogcatEnable = false

    fun enableLogcat() {
        isLogcatEnable = true
    }

    fun disableLogcat() {
        isLogcatEnable = false
    }

    fun getExtensionVersion(): String {
        return "${BuildConfig.VERSION_NAME}-${BuildConfig.EX_FLAVOR} (${BuildConfig.VERSION_CODE})"
    }

    fun log(tag: String, msg: String) {
        if (isLogcatEnable) {
            XposedBridge.log("$tag: $msg")
        }
    }

}