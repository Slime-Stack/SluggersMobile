package com.slimestack.mlbsluggersapp.services.interfaces

import com.slimestack.mlbsluggersapp.services.implementations.HighlightsResponse

interface SluggersHighlightsServiceInterface {
    @Throws(Exception::class)
    suspend fun getHighlights(url: String): HighlightsResponse
}
