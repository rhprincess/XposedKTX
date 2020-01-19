package io.rhprincess.xposed_ktx_lib

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.XModuleResources
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.callbacks.XC_InitPackageResources
import de.robv.android.xposed.callbacks.XC_LoadPackage
import io.rhprincess.xposed_ktx.findViewById
import io.rhprincess.xposed_ktx.hook
import io.rhprincess.xposed_ktx.impl.XposedInitializer
import io.rhprincess.xposed_ktx.resHook

private const val selfPkg = "io.github.yabaiofficial.xposed_ktx_lib"
private const val selfPkgMainActivity = "io.github.yabaiofficial.xposed_ktx_lib.MainActivity"

class XposedInit : XposedInitializer() {

    private var MODULE_PATH = ""

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam?) {
        when (lpparam!!.packageName) {
            selfPkg -> {

                selfPkgMainActivity.hook {
                    classLoader = lpparam.classLoader
                    methodName = "onCreate"
                    parameterTypes = Bundle::class.java
                    after {
                        Log.e("Xposed KTX", "I'm successfully been hooked")
                        val activity = it.thisObject as Activity
                        Toast.makeText(activity, "You had been slain", Toast.LENGTH_LONG).show()
                    }
                }

            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun handleInitPackageResources(resparam: XC_InitPackageResources.InitPackageResourcesParam?) {
        when (resparam!!.packageName) {
            selfPkg -> {

                selfPkg.resHook {
                    type = "layout"
                    name = "activity_main"
                    resp = resparam
                    modRes = XModuleResources.createInstance(MODULE_PATH, resparam.res) //创建模块资源
                    replace {
                        type = "drawable"; name = "ic_chart"; replacement =
                        modRes!!.fwd(R.drawable.ic_xp)
                    } //替换一个图片资源
                    layoutLoaded {
                        val tv = it.findViewById("tv") as TextView
                        tv.setTextColor(Color.parseColor("#FF0000"))
                        tv.text = "From Xposed: You had been slain!"
                    }
                }

            }
        }
    }

    override fun initZygote(startupParam: IXposedHookZygoteInit.StartupParam?) {
        MODULE_PATH = startupParam!!.modulePath
    }
}