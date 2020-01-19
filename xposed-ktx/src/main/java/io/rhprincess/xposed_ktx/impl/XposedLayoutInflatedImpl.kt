package io.rhprincess.xposed_ktx.impl

import de.robv.android.xposed.callbacks.XC_LayoutInflated

interface XposedLayoutInflatedImpl {
    fun layoutLoaded(liparam: (XC_LayoutInflated.LayoutInflatedParam) -> Unit)
}