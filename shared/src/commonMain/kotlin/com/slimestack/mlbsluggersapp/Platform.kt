package com.slimestack.mlbsluggersapp

interface Platform {
    val name: String
    val language: String
    val country: String
}

expect fun getPlatform(): Platform