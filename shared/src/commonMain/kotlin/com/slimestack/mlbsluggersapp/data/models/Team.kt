package com.slimestack.mlbsluggersapp.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Team(
    val teamId: Int,
    val name: String,
    val shortName: String,
    val logoUrl: String
)
