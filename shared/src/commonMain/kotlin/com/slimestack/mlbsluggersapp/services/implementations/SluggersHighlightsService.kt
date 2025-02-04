package com.slimestack.mlbsluggersapp.services.implementations

import co.touchlab.kermit.Logger
import com.slimestack.mlbsluggersapp.data.models.Highlight
import com.slimestack.mlbsluggersapp.network.implementations.HttpClientFactory
import com.slimestack.mlbsluggersapp.network.implementations.MLBSluggersClient
import com.slimestack.mlbsluggersapp.network.interfaces.MLBSluggersClientInterface
import com.slimestack.mlbsluggersapp.services.interfaces.SluggersHighlightsServiceInterface
import io.ktor.client.call.body
import kotlinx.serialization.Serializable

class SluggersHighlightsService(
    private val client: MLBSluggersClientInterface,
    private val logger: Logger,
) : SluggersHighlightsServiceInterface {
    constructor(baseUrl: String) : this(
        client = MLBSluggersClient(
            baseUrl = baseUrl,
            httpClientFactory = HttpClientFactory(),
            logger = Logger,
            tag = "SluggersHighlightsService"
        ),
        logger = Logger,
    )

    @Throws(Exception::class)
    override suspend fun getHighlights(url: String): HighlightsResponse {
        val response = client.getClientHttpResponse(
            endpoint = url,
            parameters = mapOf()
        )
        return try {
            if (response.status.value in 200..299) {
                logger.withTag("SluggersHighlightService").i("Retrieving highlights...")
                response.body<HighlightsResponse>()
            } else {
                val error = "Failed to get highlights. Error code: ${response.status.value}: ${response.status.description}"
                logger.withTag("SluggersHighlightService").e(error)
                throw Exception(error)
            }
        } catch (exception: Exception) {
            logger.withTag("SluggersHighlightService").e(exception.message ?: "Failed with an unknown exception message")
            throw exception
        } finally {
            client.closeClient()
        }
    }
}

@Serializable
data class HighlightsResponse(val highlights: List<Highlight>)
