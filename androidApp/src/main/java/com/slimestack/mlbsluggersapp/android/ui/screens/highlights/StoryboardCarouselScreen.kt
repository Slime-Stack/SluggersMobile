package com.slimestack.mlbsluggersapp.android.ui.screens.highlights

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import coil3.compose.AsyncImage
import com.slimestack.mlbsluggersapp.android.R
import com.slimestack.mlbsluggersapp.data.models.Storyboard
import com.slimestack.mlbsluggersapp.data.test_data.HighlightsHelper
import kotlinx.serialization.json.Json
import java.util.Locale

@Composable
fun StoryboardCarouselScreen(storyboard: Storyboard) {
    var currentSceneIndex by remember { mutableIntStateOf(0) }
    val currentScene = storyboard.scenes[currentSceneIndex]
    val context = LocalContext.current
    val audioPlayer = remember { ExoPlayer.Builder(context).build() }
    val deviceLanguage = Locale.getDefault().language
    var audioProgress by remember { mutableFloatStateOf(0f) }

    fun playAudio(audioUrl: String) {
        audioPlayer.stop()
        audioPlayer.clearMediaItems()
        if (audioUrl.isNotEmpty()) {
            val mediaItem = MediaItem.fromUri(audioUrl)
            audioPlayer.setMediaItem(mediaItem)
            audioPlayer.prepare()
            audioPlayer.play()
        }
    }

    fun onNextScene(){
        if (currentSceneIndex < storyboard.scenes.size - 1) {
            currentSceneIndex++
        }
    }

    fun onPreviousScene() {
        if (currentSceneIndex > 0) {
            currentSceneIndex--
        }
    }


    LaunchedEffect(currentSceneIndex) {
        val audioUrl = when (deviceLanguage) {
            "es" -> currentScene.audioUrlEs
            "ja" -> currentScene.audioUrlJa
            else -> currentScene.audioUrlEn
        }
        playAudio(audioUrl)
    }
    LaunchedEffect(audioPlayer.isPlaying) {
        audioProgress = if (audioPlayer.isPlaying) {
            audioPlayer.currentPosition.toFloat() / audioPlayer.duration.toFloat()
        } else {
            0f
        }
    }


    Box(modifier = Modifier.fillMaxSize()) {
        //Image
        AsyncImage(
            model = currentScene.imageUrl,
            contentDescription = currentScene.visualDescription,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
            error = painterResource(R.drawable.game_775296_scene_1)
        )
//        Image(
//            painter = rememberAsyncImagePainter(currentScene.imageUrl),
//            contentDescription = currentScene.visualDescription,
//            modifier = Modifier.fillMaxSize(),
//            contentScale = ContentScale.Crop
//        )
        // Overlay for text and navigation
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LinearProgressIndicator(
                progress = audioProgress,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                color = Color.White
            )
            // Caption
            val caption = when (deviceLanguage) {
                "es" -> currentScene.captionEs
                "ja" -> currentScene.captionJa
                else -> currentScene.captionEn
            }

            Text(
                text = caption,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(1f))


            //Navigation
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {

                if(currentSceneIndex > 0){
                    Button(onClick = { onPreviousScene() }) {
                        Text("Previous")
                    }
                }else{
                    Spacer(modifier = Modifier.size(0.dp))
                }


                if(currentSceneIndex < storyboard.scenes.size - 1){
                    Button(onClick = { onNextScene() }) {
                        Text("Next")
                    }
                }else{
                    Spacer(modifier = Modifier.size(0.dp))
                }

            }
        }

    }
    //ExoPlayer
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
    val storyboard = Json.decodeFromString<Storyboard>(jsonString)
    StoryboardCarouselScreen(storyboard = storyboard)
}
