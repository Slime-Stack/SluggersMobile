package com.slimestack.mlbsluggersapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform