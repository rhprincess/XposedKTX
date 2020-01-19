package io.rhprincess.xposed_ktx.impl

import de.robv.android.xposed.XposedHelpers

interface XposedHelpersImpl {
    fun getBooleanField(obj: Any, fieldName: String): Boolean {
        return XposedHelpers.getBooleanField(obj, fieldName)
    }

    fun getCharField(obj: Any, fieldName: String): Char {
        return XposedHelpers.getCharField(obj, fieldName)
    }

    fun getDoubleField(obj: Any, fieldName: String): Double {
        return XposedHelpers.getDoubleField(obj, fieldName)
    }

    fun getFloatField(obj: Any, fieldName: String): Float {
        return XposedHelpers.getFloatField(obj, fieldName)
    }

    fun getIntField(obj: Any, fieldName: String): Int {
        return XposedHelpers.getIntField(obj, fieldName)
    }

    fun getLongField(obj: Any, fieldName: String): Long {
        return XposedHelpers.getLongField(obj, fieldName)
    }

    fun getObjectField(obj: Any, fieldName: String): Any {
        return XposedHelpers.getObjectField(obj, fieldName)
    }

    fun getShortField(obj: Any, fieldName: String): Short {
        return XposedHelpers.getShortField(obj, fieldName)
    }


    fun getStaticBooleanField(clazz: Class<*>, fieldName: String): Boolean {
        return XposedHelpers.getStaticBooleanField(clazz, fieldName)
    }

    fun getStaticCharField(clazz: Class<*>, fieldName: String): Char {
        return XposedHelpers.getStaticCharField(clazz, fieldName)
    }

    fun getStaticDoubleField(clazz: Class<*>, fieldName: String): Double {
        return XposedHelpers.getStaticDoubleField(clazz, fieldName)
    }

    fun getStaticFloatField(clazz: Class<*>, fieldName: String): Float {
        return XposedHelpers.getStaticFloatField(clazz, fieldName)
    }

    fun getStaticIntField(clazz: Class<*>, fieldName: String): Int {
        return XposedHelpers.getStaticIntField(clazz, fieldName)
    }

    fun getStaticLongField(clazz: Class<*>, fieldName: String): Long {
        return XposedHelpers.getStaticLongField(clazz, fieldName)
    }

    fun getStaticObjectField(clazz: Class<*>, fieldName: String): Any {
        return XposedHelpers.getStaticObjectField(clazz, fieldName)
    }

    fun getStaticShortField(clazz: Class<*>, fieldName: String): Short {
        return XposedHelpers.getStaticShortField(clazz, fieldName)
    }
}