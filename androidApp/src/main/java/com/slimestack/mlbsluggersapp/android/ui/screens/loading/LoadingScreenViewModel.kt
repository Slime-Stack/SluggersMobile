package com.slimestack.mlbsluggersapp.android.ui.screens.loading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slimestack.mlbsluggersapp.android.dataModel.sealed.TeamDetailsUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoadingScreenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<TeamDetailsUiState>(TeamDetailsUiState.Loading)
    val uiState: StateFlow<TeamDetailsUiState> = _uiState.asStateFlow()

    private val _triviaText = MutableStateFlow("")
    val triviaText: StateFlow<String> = _triviaText.asStateFlow()

    private val triviaList = listOf(
        "Did you know that the first professional baseball game was played in 1869?",
        "The longest baseball game ever played lasted 33 innings!",
        "The fastest pitch ever recorded was 105.1 mph!",
        "The most home runs hit in a single season is 73!",
        "The most stolen bases in a single season is 130!",
        "The most strikeouts in a single season is 383!",
        "The most walks in a single season is 170!",
        "The most hits in a single season is 262!",
        "The most runs in a single season is 177!",
        "The most RBIs in a single season is 191!"
    )

    init {
        startTriviaAnimation()
    }

    fun fetchTeamDetails(teamId: Int) {
        viewModelScope.launch {
            _uiState.value = TeamDetailsUiState.Loading
            try {
                val teamDetails = simulateNetworkRequest(teamId)
                _uiState.value = TeamDetailsUiState.Success(teamDetails)
            } catch (e: Exception) {
                _uiState.value = TeamDetailsUiState.Error("Failed to fetch team details")
            }
        }
    }

    private fun startTriviaAnimation() {
        viewModelScope.launch {
            while (_uiState.value is TeamDetailsUiState.Loading) {
                for (trivia in triviaList) {
                    _triviaText.value = trivia
                    delay(3000) // Show each trivia for 3 seconds
                }
            }
        }
    }

    private suspend fun simulateNetworkRequest(teamId: Int): String {
        // Replace this with your actual network request logic
        kotlinx.coroutines.delay(500) // Simulate a 20-second delay
        return "Details for team $teamId"
    }
}
