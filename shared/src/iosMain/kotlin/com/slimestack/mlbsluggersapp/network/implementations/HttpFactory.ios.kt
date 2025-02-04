package com.slimestack.mlbsluggersapp.network.implementations

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

actual fun provideKtorClient(): HttpClient =
    HttpClient(Darwin) {
        engine {
            configureRequest {
                setAllowsCellularAccess(true)
            }
        }
    }
