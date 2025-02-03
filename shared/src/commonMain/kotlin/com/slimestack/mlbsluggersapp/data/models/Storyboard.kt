package com.slimestack.mlbsluggersapp.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Storyboard(
    val storyTitle: String,
    val teaserSummary: String,
    val storyImageUrl: String,
    val storyImagenPrompt: String,
    val storyImageResId: Int? = null,
    val scenes: List<Scene>
)
