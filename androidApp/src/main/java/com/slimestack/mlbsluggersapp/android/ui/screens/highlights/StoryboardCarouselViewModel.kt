package com.slimestack.mlbsluggersapp.android.ui.screens.highlights

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slimestack.mlbsluggersapp.android.ui.state.sealed.StoryboardCarouselUiState
import com.slimestack.mlbsluggersapp.data.test_data.HighlightsHelper
import com.slimestack.mlbsluggersapp.repositories.implementations.HighlightsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StoryboardCarouselViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<StoryboardCarouselUiState>(StoryboardCarouselUiState.Loading)
    val uiState: StateFlow<StoryboardCarouselUiState> = _uiState.asStateFlow()
    private val _triviaText = MutableStateFlow("")
    val triviaText: StateFlow<String> = _triviaText.asStateFlow()

    fun fetchStoryboard(gameId: Int) {
        viewModelScope.launch {
            _uiState.value = StoryboardCarouselUiState.Loading
            try {
                val storyboardJson = when (gameId) {
                    775294 -> HighlightsHelper.storyboardJsonStr775294
                    775296 -> HighlightsHelper.storyboardJsonStr775296
                    775297 -> HighlightsHelper.storyboardJsonStr775297
                    775298 -> HighlightsHelper.storyboardJsonStr775298
                    775300 -> HighlightsHelper.storyboardJsonStr775300
                    else -> {
                        Log.e(
                            "StoryboardCarouselViewModel",
                            "fetchStoryboard: Storyboard not found for gameId: $gameId",
                        )
                        _uiState.value = StoryboardCarouselUiState.Error("Storyboard not found")
                        return@launch
                    }
                }
                val storyBoard =
                    HighlightsRepository().fetchGameStoryboardFromJsonString(storyboardJson)
                _uiState.value = StoryboardCarouselUiState.Success(storyBoard)
            } catch (e: Exception) {
                _uiState.value = StoryboardCarouselUiState.Error("Failed to fetch storyboard")
            }
        }
    }

}