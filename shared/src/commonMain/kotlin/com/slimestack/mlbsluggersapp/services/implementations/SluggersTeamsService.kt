package com.slimestack.mlbsluggersapp.services.implementations

import co.touchlab.kermit.Logger
import com.slimestack.mlbsluggersapp.data.models.Team
import com.slimestack.mlbsluggersapp.network.implementations.HttpClientFactory
import com.slimestack.mlbsluggersapp.network.implementations.MLBSluggersClient
import com.slimestack.mlbsluggersapp.network.interfaces.MLBSluggersClientInterface
import com.slimestack.mlbsluggersapp.services.interfaces.SluggersTeamsServiceInterface
import io.ktor.client.call.body

class SluggersTeamsService(
    private val client: MLBSluggersClientInterface,
    private val logger: Logger,
) : SluggersTeamsServiceInterface {
    constructor(baseUrl: String) : this(
        client = MLBSluggersClient(
            baseUrl = baseUrl,
            httpClientFactory = HttpClientFactory(),
            logger = Logger,
            tag = "SluggersTeamsService"
        ),
        logger = Logger,
    )

    @Throws(Exception::class)
    override suspend fun getTeams(url: String): List<Team> {
        val response = client.getClientHttpResponse(
            endpoint = url,
            parameters = mapOf(),
        )
        return try {
            if (response.status.value in 200..299) {
                response.body<List<Team>>()
            } else {
                val error = "Failed to get response. Error code: ${response.status.value}"
                throw Exception(error)
            }
        } catch (exception: Exception) {
            throw exception
        } finally {
            client.closeClient()
        }
    }
}
