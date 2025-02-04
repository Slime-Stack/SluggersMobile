package com.slimestack.mlbsluggersapp.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Team(
    val logoUrl: String,
    val name: String,
    val shortName: String,
    val teamId: Int
)
