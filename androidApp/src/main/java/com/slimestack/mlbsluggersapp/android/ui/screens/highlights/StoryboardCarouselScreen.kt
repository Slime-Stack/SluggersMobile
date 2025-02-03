package com.slimestack.mlbsluggersapp.android.ui.screens.highlights

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import coil3.compose.AsyncImage
import com.slimestack.mlbsluggersapp.android.R
import com.slimestack.mlbsluggersapp.data.models.Storyboard
import com.slimestack.mlbsluggersapp.data.test_data.HighlightsHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.togetherWith
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

@Composable
fun StoryboardCarouselScreen(storyboard: Storyboard = remember { HighlightsHelper.fetchStoryboard(775296) }) {
    var currentSceneIndex by remember { mutableIntStateOf(0) }
    val currentScene = storyboard.scenes[currentSceneIndex]
    val context = LocalContext.current
    val audioPlayer = remember { ExoPlayer.Builder(context).build() }
    val deviceLanguage = Locale.getDefault().language
    var audioProgress by remember { mutableFloatStateOf(0f) }
    var shouldAdvanceScene by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {
        // Wrap the AsyncImage in AnimatedContent for smooth transitions
        AnimatedContent(
            targetState = currentScene,
            transitionSpec = {
                // Slide and fade animation
                (slideInHorizontally { width -> width } + fadeIn(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                )).togetherWith(
                    slideOutHorizontally { width -> -width } + fadeOut(
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = LinearEasing
                        )
                    )
                )
            },
            label = "scene transition"
        ) { targetScene ->
            AsyncImage(
                model = targetScene.imageUrl,
                contentDescription = targetScene.visualDescription,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.game_775296_scene_1)
            )
        }

        // Left tap area (previous)
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.3f)
                .clickable {
                    if (currentSceneIndex > 0) {
                        currentSceneIndex--
                    }
                }
        )
        
        // Center tap area (pause/play)
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.4f)
                .align(Alignment.TopCenter)
                .clickable {
                    if (audioPlayer.isPlaying) {
                        audioPlayer.pause()
                    } else {
                        audioPlayer.play()
                    }
                }
        )

        // Right tap area (next)
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.3f)
                .align(Alignment.TopEnd)
                .clickable {
                    if (currentSceneIndex < storyboard.scenes.size - 1) {
                        currentSceneIndex++
                    }
                }
        )

        // Wrap the progress indicators in Crossfade
        Crossfade(
            targetState = currentSceneIndex,
            animationSpec = tween(durationMillis = 1000),
            label = "progress indicator transition"
        ) { targetIndex ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .align(Alignment.TopCenter),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                storyboard.scenes.forEachIndexed { index, _ ->
                    LinearProgressIndicator(
                        progress = if (index < targetIndex) {
                            1f
                        } else if (index == targetIndex) {
                            audioProgress
                        } else {
                            0f
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(4.dp),
                        color = Color.White
                    )
                }
            }
        }

        // Wrap the caption in AnimatedContent
        AnimatedContent(
            targetState = when (deviceLanguage) {
                "es" -> currentScene.captionEs
                "ja" -> currentScene.captionJa
                else -> currentScene.captionEn
            },
            transitionSpec = {
                fadeIn(animationSpec = tween(300)) togetherWith 
                fadeOut(animationSpec = tween(300))
            },
            label = "caption transition"
        ) { caption ->
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f))
                        )
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = caption,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    // Audio handling with auto-advance
    LaunchedEffect(currentSceneIndex) {
        shouldAdvanceScene = false
        val audioUrl = when (deviceLanguage) {
            "es" -> currentScene.audioUrlEs
            "ja" -> currentScene.audioUrlJa
            else -> currentScene.audioUrlEn
        }
        audioPlayer.stop()
        audioPlayer.clearMediaItems()
        if (audioUrl.isNotEmpty()) {
            val mediaItem = MediaItem.fromUri(audioUrl)
            audioPlayer.setMediaItem(mediaItem)
            audioPlayer.prepare()
            audioPlayer.play()
            
            // Monitor for audio completion
            launch {
                while (!shouldAdvanceScene) {
                    if (audioPlayer.playbackState == Player.STATE_ENDED) {
                        delay(500) // Wait 3 seconds after audio completes
                        if (currentSceneIndex < storyboard.scenes.size - 1) {
                            currentSceneIndex++
                        }
                        shouldAdvanceScene = true
                    }
                    delay(100) // Check every 100ms
                }
            }
        } else {
            // If no audio, wait 5 seconds before advancing
            launch {
                delay(5000)
                if (!shouldAdvanceScene && currentSceneIndex < storyboard.scenes.size - 1) {
                    currentSceneIndex++
                    shouldAdvanceScene = true
                }
            }
        }
    }

    // Update progress
    LaunchedEffect(Unit) {
        while (true) {
            if (audioPlayer.duration > 0) {
                audioProgress = audioPlayer.currentPosition.toFloat() / audioPlayer.duration.toFloat()
            }
            delay(16) // Update roughly 60 times per second
        }
    }

    // Cleanup
    DisposableEffect(Unit) {
        onDispose {
            audioPlayer.release()
        }
    }
}

@Preview
@Composable
fun StoryboardCarouselPreview() {
    val jsonString = HighlightsHelper.storyboardJsonStr775294
    val storyboard = HighlightsHelper.fetchStoryboard(775296)
    StoryboardCarouselScreen(storyboard)
}
