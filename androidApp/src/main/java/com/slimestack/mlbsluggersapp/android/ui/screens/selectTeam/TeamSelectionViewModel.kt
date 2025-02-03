package com.slimestack.mlbsluggersapp.android.ui.screens.selectTeam

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slimestack.mlbsluggersapp.data.models.Team
import com.slimestack.mlbsluggersapp.repositories.implementations.TeamsRepository
import com.slimestack.mlbsluggersapp.repositories.interfaces.TeamsRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TeamSelectionViewModel(
    private val teamsRepository: TeamsRepositoryInterface = TeamsRepository
) : ViewModel() {
    private val _teams = MutableStateFlow<List<Team>>(emptyList())
    val teams: StateFlow<List<Team>> = _teams.asStateFlow()

    init {
        fetchTeams()
    }

    private fun fetchTeams() {
        viewModelScope.launch {
            val teams = teamsRepository.fetchTeams()
            _teams.update { teams }
        }
    }
}
