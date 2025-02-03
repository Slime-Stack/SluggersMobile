package com.slimestack.mlbsluggersapp.android.dataModel.sealed

sealed class TeamDetailsUiState {
    data object Loading : TeamDetailsUiState()
    data class Success(val teamDetails: String) : TeamDetailsUiState()
    data class Error(val message: String) : TeamDetailsUiState()
}
