package com.slimestack.mlbsluggersapp.android.ui.screens.highlights

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.slimestack.mlbsluggersapp.android.ui.state.sealed.StoryboardCarouselUiState
import com.slimestack.mlbsluggersapp.repositories.implementations.HighlightsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StoryboardCarouselViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<StoryboardCarouselUiState>(StoryboardCarouselUiState.Loading)
    val uiState: StateFlow<StoryboardCarouselUiState> = _uiState.asStateFlow()
    private val highlightsRepository = HighlightsRepository()
    
    private var _audioPlayer: ExoPlayer? = null
    val audioPlayer: ExoPlayer?
        get() = _audioPlayer

    fun initializePlayer(context: Context) {
        if (_audioPlayer == null) {
            _audioPlayer = ExoPlayer.Builder(context).build().apply {
                playWhenReady = true
                repeatMode = Player.REPEAT_MODE_OFF
            }
        }
    }

    fun fetchStoryboard(gameId: Int, teamId: Int) {
        viewModelScope.launch {
            try {
                val highlights = highlightsRepository.fetchHighlightsByTeamId(teamId.toString()).highlights
                val gameHighlight = highlights.find { it.gamePk == gameId.toString() }
                val storyBoard = gameHighlight?.storyboard ?: error("Storyboard not found")
                _uiState.value = StoryboardCarouselUiState.Loading

                _uiState.value = StoryboardCarouselUiState.Success(storyBoard)
            } catch (e: Exception) {
                _uiState.value = StoryboardCarouselUiState.Error("Failed to fetch storyboard")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        _audioPlayer?.release()
        _audioPlayer = null
    }
}