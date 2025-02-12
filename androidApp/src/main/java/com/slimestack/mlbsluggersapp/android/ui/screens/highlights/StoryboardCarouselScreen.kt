package com.slimestack.mlbsluggersapp.android.ui.screens.highlights

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import coil3.compose.AsyncImage
import com.slimestack.mlbsluggersapp.android.R
import com.slimestack.mlbsluggersapp.data.models.Storyboard
import com.slimestack.mlbsluggersapp.data.test_data.HighlightsHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale
import androidx.lifecycle.viewmodel.compose.viewModel
import com.slimestack.mlbsluggersapp.android.ui.utils.UIConstants.CAPTION_TRANSITION_LABEL
import com.slimestack.mlbsluggersapp.android.ui.utils.UIConstants.ES_LANGUAGE_CODE
import com.slimestack.mlbsluggersapp.android.ui.utils.UIConstants.JA_LANGUAGE_CODE
import com.slimestack.mlbsluggersapp.android.ui.utils.UIConstants.PROGRESS_INDICATOR_TRANSITION_LABEL
import com.slimestack.mlbsluggersapp.android.ui.utils.UIConstants.SCENE_TRANSITION_LABEL

@Composable
fun StoryboardCarouselScreen(
    storyboard: Storyboard,
    viewModel: StoryboardCarouselViewModel = viewModel(),
    onNavigateBack: () -> Unit = {}
) {
    var currentSceneIndex by remember { mutableIntStateOf(0) }
    val currentScene = storyboard.scenes[currentSceneIndex]
    val context = LocalContext.current
    val deviceLanguage = Locale.getDefault().language
    var audioProgress by remember { mutableFloatStateOf(0f) }
    var shouldAdvanceScene by remember { mutableStateOf(false) }

    val largeRadialGradient = object : ShaderBrush() {
        override fun createShader(size: Size): Shader {
            val biggerDimension = maxOf(size.height, size.width)
            return LinearGradientShader(
                from = size.center + Offset(0f, biggerDimension / 4f),
                to = size.center + Offset(0f, biggerDimension / 2f),
                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.9f)),
                colorStops = listOf(0f, 0.95f),

            )
        }
    }
    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {
        Column {
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(4f),
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
                    label = SCENE_TRANSITION_LABEL
                ) { targetScene ->
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxSize(),
                        model = targetScene.imageUrl,
                        contentDescription = targetScene.visualDescription,
                        contentScale = ContentScale.Crop,
                        error = painterResource(R.drawable.game_775296_scene_1)
                    )
                    Box(Modifier.fillMaxSize().background(largeRadialGradient))
                }
            }

            // Caption with background
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 48.dp,
                        shape = RoundedCornerShape(16.dp),
                        clip = false,
                        ambientColor = Color.Black.copy(alpha = 0.6f)
                    )
                    .weight(1f)
            ) {
                AnimatedContent(
                    targetState = when (deviceLanguage) {
                        ES_LANGUAGE_CODE -> currentScene.captionEs
                        JA_LANGUAGE_CODE -> currentScene.captionJa
                        else -> currentScene.captionEn
                    },
                    transitionSpec = {
                        fadeIn(animationSpec = tween(300)) togetherWith
                                fadeOut(animationSpec = tween(300))
                    },
                    label = CAPTION_TRANSITION_LABEL
                ) { caption ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .shadow(
                                elevation = 48.dp,
                                shape = RoundedCornerShape(16.dp),
                                clip = false,
                                ambientColor = Color.Black.copy(alpha = 0.6f)
                            )
                    ) {
                        Text(
                            text = caption,
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .background(Color.Transparent)
                        )
                    }
                }
            }
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
                    if (viewModel.audioPlayer?.isPlaying == true) {
                        viewModel.audioPlayer?.pause()
                    } else {
                        viewModel.audioPlayer?.play()
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
            animationSpec = tween(durationMillis = 600),
            label = PROGRESS_INDICATOR_TRANSITION_LABEL
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

        // Close Button
        Box(modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(top = 24.dp, end = 8.dp)
            .clickable {
                onNavigateBack.invoke()
            }
        ){
            IconButton(onClick = { onNavigateBack() } ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = stringResource(id = R.string.close_btn),
                    tint = Color.White
                )
            }
        }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.initializePlayer(context)
    }

    LaunchedEffect(currentScene) {
        shouldAdvanceScene = false
        val audioUrl = when (deviceLanguage) {
            ES_LANGUAGE_CODE -> currentScene.audioUrlEs
            JA_LANGUAGE_CODE -> currentScene.audioUrlJa
            else -> currentScene.audioUrlEn
        }
        
        viewModel.audioPlayer?.stop()
        viewModel.audioPlayer?.clearMediaItems()
        
        if (audioUrl.isNotEmpty()) {
            val mediaItem = MediaItem.fromUri(audioUrl)
            viewModel.audioPlayer?.setMediaItem(mediaItem)
            viewModel.audioPlayer?.prepare()
            viewModel.audioPlayer?.play()

            // Single launch for monitoring
            launch {
                while (!shouldAdvanceScene) {
                    if (viewModel.audioPlayer?.playbackState == Player.STATE_ENDED) {
                        delay(500)
                        if (currentSceneIndex < storyboard.scenes.size - 1) {
                            currentSceneIndex++
                        }
                        shouldAdvanceScene = true
                        break
                    }
                    delay(100)
                }
            }
        } else {
            launch {
                delay(5000)
                if (!shouldAdvanceScene && currentSceneIndex < storyboard.scenes.size - 1) {
                    currentSceneIndex++
                    shouldAdvanceScene = true
                }
            }
        }
    }

    LaunchedEffect(viewModel.audioPlayer) {
        while (true) {
            if (viewModel.audioPlayer?.duration!! > 0) {
                audioProgress = viewModel.audioPlayer?.currentPosition?.toFloat()
                    ?: (0f / (viewModel.audioPlayer?.duration?.toFloat() ?: 0f))
            }
            delay(16)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.audioPlayer?.release()
        }
    }
}

@Preview
@Composable
fun StoryboardCarouselPreview() {
    val storyboard = HighlightsHelper.fetchStoryboard(775296)
    StoryboardCarouselScreen(storyboard)
}
