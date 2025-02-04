package com.slimestack.mlbsluggersapp.network.implementations

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android

actual fun provideKtorClient(): HttpClient =
    HttpClient(Android) {
        engine {
            connectTimeout = 100_000
            socketTimeout = 100_000
        }
    }
