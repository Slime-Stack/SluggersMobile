package com.slimestack.mlbsluggersapp.services.interfaces

import com.slimestack.mlbsluggersapp.data.models.Highlight
import com.slimestack.mlbsluggersapp.data.models.Team

interface SluggersHighlightsServiceInterface {
    @Throws(Exception::class)
    suspend fun getHighlights(url: String): List<Highlight>
}
