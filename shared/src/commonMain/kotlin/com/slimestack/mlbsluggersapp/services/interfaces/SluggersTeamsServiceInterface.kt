package com.slimestack.mlbsluggersapp.services.interfaces

import com.slimestack.mlbsluggersapp.data.models.Team

interface SluggersTeamsServiceInterface {
    @Throws(Exception::class)
    suspend fun getTeams(url: String): List<Team>
}
