package com.slimestack.mlbsluggersapp.data.models

import kotlinx.serialization.Serializable

@Serializable
data class GameScheduleData(
    val gamePk: Int,
    val date: String,
    val team: String,
    val imageUrl: String
)
