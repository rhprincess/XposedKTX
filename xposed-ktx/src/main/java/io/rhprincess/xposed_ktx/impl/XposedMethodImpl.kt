package io.rhprincess.xposed_ktx.impl

import de.robv.android.xposed.XC_MethodHook

interface XposedMethodImpl : XposedHelpersImpl {
    fun before(before: (XC_MethodHook.MethodHookParam) -> Unit)
    fun after(after: (XC_MethodHook.MethodHookParam) -> Unit)
}