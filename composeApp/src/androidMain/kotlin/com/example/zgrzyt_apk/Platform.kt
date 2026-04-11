package com.example.zgrzyt_apk

import android.os.Build

class AndroidPlatform {
    val name: String = "Android ${Build.VERSION.SDK_INT}"
}

fun getPlatform() = AndroidPlatform()