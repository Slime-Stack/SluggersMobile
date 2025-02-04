package com.slimestack.mlbsluggersapp.repositories.interfaces

import com.slimestack.mlbsluggersapp.data.models.Team

interface TeamsRepositoryInterface {
    @Throws(Exception::class)
    suspend fun fetchTeams(): List<Team>
}
