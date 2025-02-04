package com.slimestack.mlbsluggersapp.repositories.implementations

import com.slimestack.mlbsluggersapp.data.models.Storyboard
import com.slimestack.mlbsluggersapp.data.test_data.HighlightsHelper
import com.slimestack.mlbsluggersapp.repositories.interfaces.HighlightsRepositoryInterface
import com.slimestack.mlbsluggersapp.services.implementations.HighlightsResponse
import com.slimestack.mlbsluggersapp.services.implementations.SluggersHighlightsService
import com.slimestack.mlbsluggersapp.services.interfaces.SluggersHighlightsServiceInterface
import kotlinx.serialization.json.Json

class HighlightsRepository(
    private val sluggersHighlightsService: SluggersHighlightsServiceInterface = SluggersHighlightsService(
        BASE_URL)
) : HighlightsRepositoryInterface {

    override suspend fun fetchHighlightsByTeamId(teamId: String): HighlightsResponse =
        sluggersHighlightsService.getHighlights(BASE_URL + teamId)

    override suspend fun fetchGameStoryboardFromJsonString(jsonString: String): Storyboard {
        val gameString = HighlightsHelper.storyboardJsonStr775294
        val json = Json { ignoreUnknownKeys = true }
        return json.decodeFromString<Storyboard>(gameString)
    }

    companion object {
        const val BASE_URL = "https://sluggers-api-35254635395.us-central1.run.app/highlights/"
    }
}
