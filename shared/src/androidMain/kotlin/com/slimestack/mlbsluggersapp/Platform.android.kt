package com.slimestack.mlbsluggersapp

import android.os.Build.VERSION
import java.util.Locale

class AndroidPlatform : Platform {
    private val deviceLocale = Locale.getDefault()
    override val name: String = "Android ${VERSION.SDK_INT}"
    override val language: String = deviceLocale.language
    override val country: String = deviceLocale.country
}

actual fun getPlatform(): Platform = AndroidPlatform()