package com.slimestack.mlbsluggersapp.network.implementations

import co.touchlab.kermit.Logger
import com.slimestack.mlbsluggersapp.getPlatform
import com.slimestack.mlbsluggersapp.network.interfaces.HttpClientFactoryInterface
import com.slimestack.mlbsluggersapp.network.interfaces.MLBSluggersClientInterface
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class MLBSluggersClient(
    private val baseUrl: String,
    private val httpClientFactory: HttpClientFactoryInterface,
    private val logger: Logger,
    private val tag: String,
) : MLBSluggersClientInterface {
    override val client: HttpClient
        get() = getSluggersClient()

    override suspend fun getClientHttpResponse(
        endpoint: String,
        parameters: Map<String, Any?>
    ): HttpResponse =
        try {
            client.get(endpoint) {
                parameters.entries.map { paramEntry ->
                    parameter(paramEntry.key, paramEntry.value)
                }
            }
        } catch (exception: Exception) {
            logger.withTag(tag).e("Failed to get client response", exception)
            throw exception
        }

    override fun closeClient() {
        client.close()
    }

    private fun getSluggersClient(): HttpClient =
        httpClientFactory.getClient().run {
            config {
                install(ContentNegotiation) {
                    json(
                        Json {
                            prettyPrint = true
                            isLenient = true
                            ignoreUnknownKeys = true
                            explicitNulls = false
                            classDiscriminator = "id"
                        }
                    )
                }
                defaultRequest {
                    url(baseUrl)
                    buildJsonRequestHeaders()
                }
            }
        }

    object RequestHeaderKeys {
        const val ACCEPT = "Accept"
        const val ACCEPT_LANGUAGE = "Accept-Language"
        const val CONTENT_TYPE = "Content-Type"
        const val USER_AGENT = "User-Agent"
    }

    object RequestHeaderValues {
        const val CONTENT_TYPE_JSON = "application/json"
    }

    companion object {
        fun DefaultRequest.DefaultRequestBuilder.buildJsonRequestHeaders() {
            val platform = getPlatform()
            val acceptLanguage = "${platform.language}-${platform.country}"
            header(RequestHeaderKeys.CONTENT_TYPE, RequestHeaderValues.CONTENT_TYPE_JSON)
            header(RequestHeaderKeys.ACCEPT_LANGUAGE, acceptLanguage)
            header(RequestHeaderKeys.USER_AGENT, platform.name)
            header(RequestHeaderKeys.ACCEPT, RequestHeaderValues.CONTENT_TYPE_JSON)
            contentType(ContentType.Application.Json)
        }

        @Throws(Exception::class)
        suspend inline fun <reified T> HttpResponse.getResponseBody(
            tag: String,
            logger: Logger,
        ): T =
            if (status.value in 200..299) {
                try {
                    this.body()
                } catch (exception: Exception) {
                    logger.withTag(tag).e("Failed to parse response", exception)
                    throw exception
                }
            } else {
                val error = "error code: ${status.value}"
                logger.withTag(tag).e(error)
                throw Exception(error)
            }
    }
}
