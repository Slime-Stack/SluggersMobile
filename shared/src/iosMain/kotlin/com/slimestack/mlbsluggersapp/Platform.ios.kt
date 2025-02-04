package com.slimestack.mlbsluggersapp

import platform.Foundation.NSLocale
import platform.Foundation.countryCode
import platform.Foundation.currentLocale
import platform.Foundation.languageCode
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    private val deviceLocale = NSLocale.currentLocale
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
    override val language: String = deviceLocale.languageCode
    override val country: String = deviceLocale.countryCode.toString()
}

actual fun getPlatform(): Platform = IOSPlatform()