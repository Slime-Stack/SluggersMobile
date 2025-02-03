package com.slimestack.mlbsluggersapp.repositories.interfaces

import com.slimestack.mlbsluggersapp.data.models.Highlight
import com.slimestack.mlbsluggersapp.data.models.Storyboard

interface HighlightsRepositoryInterface {
    @Throws(Exception::class)
    suspend fun fetchHighlightsByTeamId(teamId: String): List<Highlight>

    suspend fun fetchGameStoryboardFromJsonString(jsonString: String): Storyboard
}
