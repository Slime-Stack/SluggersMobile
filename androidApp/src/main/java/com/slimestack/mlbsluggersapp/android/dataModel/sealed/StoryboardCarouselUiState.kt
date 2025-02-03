package com.slimestack.mlbsluggersapp.android.dataModel.sealed

import com.slimestack.mlbsluggersapp.data.models.Storyboard

sealed class StoryboardCarouselUiState {
    data object Loading : StoryboardCarouselUiState()
    data class Success(val storyboard: Storyboard) : StoryboardCarouselUiState()
    data class Error(val message: String) : StoryboardCarouselUiState()
}
