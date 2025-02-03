package com.slimestack.mlbsluggersapp.repositories.implementations

import com.slimestack.mlbsluggersapp.data.models.Team
import com.slimestack.mlbsluggersapp.repositories.interfaces.TeamsRepositoryInterface
import com.slimestack.mlbsluggersapp.services.implementations.SluggersTeamsService
import com.slimestack.mlbsluggersapp.services.interfaces.SluggersTeamsServiceInterface

object TeamsRepository : TeamsRepositoryInterface {
    private val sluggersTeamsService: SluggersTeamsServiceInterface = SluggersTeamsService("https://sluggers-api-35254635395.us-central1.run.app/")
    @Throws(Exception::class)
    override suspend fun fetchTeams(): List<Team> {
        return sluggersTeamsService.getTeams("teams")
    }
}
