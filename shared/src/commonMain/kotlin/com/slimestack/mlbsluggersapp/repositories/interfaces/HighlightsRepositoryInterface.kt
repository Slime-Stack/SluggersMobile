package com.slimestack.mlbsluggersapp.repositories.interfaces

import com.slimestack.mlbsluggersapp.data.models.Storyboard
import com.slimestack.mlbsluggersapp.services.implementations.HighlightsResponse

interface HighlightsRepositoryInterface {
    @Throws(Exception::class)
    suspend fun fetchHighlightsByTeamId(teamId: String): HighlightsResponse

    suspend fun fetchGameStoryboardFromJsonString(jsonString: String): Storyboard
}
