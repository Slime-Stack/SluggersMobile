package com.slimestack.mlbsluggersapp.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Highlight(
    val createdAt: String,
    val gameDate: String,
    val scenes: List<Scene>,
    val teams: List<String>,
    val updatedAt: String
)
