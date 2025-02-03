package com.slimestack.mlbsluggersapp.network.interfaces

import io.ktor.client.HttpClient
import io.ktor.client.statement.HttpResponse

interface MLBSluggersClientInterface {
    val client: HttpClient

    @Throws(Exception::class)
    suspend fun getClientHttpResponse(
        endpoint: String,
        parameters: Map<String, Any?>,
    ): HttpResponse

    fun closeClient()
}
