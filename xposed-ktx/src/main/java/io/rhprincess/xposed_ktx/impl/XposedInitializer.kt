package io.rhprincess.xposed_ktx.impl

import de.robv.android.xposed.IXposedHookInitPackageResources
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.callbacks.XC_InitPackageResources
import de.robv.android.xposed.callbacks.XC_LoadPackage

open class XposedInitializer : IXposedHookLoadPackage, IXposedHookZygoteInit,
    IXposedHookInitPackageResources {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam?) {
        //TODO "This method will be took place if you have implemented it."
    }

    override fun initZygote(startupParam: IXposedHookZygoteInit.StartupParam?) {
        //TODO "This method will be took place if you have implemented it."
    }

    override fun handleInitPackageResources(resparam: XC_InitPackageResources.InitPackageResourcesParam?) {
        //TODO "This method will be took place if you have implemented it."
    }
}