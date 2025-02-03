package com.slimestack.mlbsluggersapp.network.interfaces

import io.ktor.client.HttpClient

interface HttpClientFactoryInterface {
    fun getClient(): HttpClient
}
