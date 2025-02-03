package com.slimestack.mlbsluggersapp.network.implementations

import com.slimestack.mlbsluggersapp.network.interfaces.HttpClientFactoryInterface
import io.ktor.client.HttpClient

class HttpClientFactory : HttpClientFactoryInterface {
    override fun getClient(): HttpClient = provideKtorClient()
}

expect fun provideKtorClient(): HttpClient
