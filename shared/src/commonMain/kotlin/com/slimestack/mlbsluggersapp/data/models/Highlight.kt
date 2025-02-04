package com.slimestack.mlbsluggersapp.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Highlight(
    val awayTeam: Int,
    val gameDate: String,
    val gamePk: String,
    val homeTeam: Int,
    val status: String,
    val storyboard: Storyboard
)
