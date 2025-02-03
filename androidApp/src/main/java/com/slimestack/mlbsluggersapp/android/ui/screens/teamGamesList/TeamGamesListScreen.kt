package com.slimestack.mlbsluggersapp.android.ui.screens.teamGamesList

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.slimestack.mlbsluggersapp.android.ui.components.baseComposables.BaseBackground
import com.slimestack.mlbsluggersapp.android.ui.components.teamGamesList.GamesListHeaderSection
import com.slimestack.mlbsluggersapp.android.ui.components.teamGamesList.GamesListSection
import com.slimestack.mlbsluggersapp.android.ui.screens.highlights.StoryboardCarouselViewModel
import com.slimestack.mlbsluggersapp.data.models.GameScheduleData
import com.slimestack.mlbsluggersapp.data.models.Team

@Composable
fun TeamGamesListScreen(
    team: Team,
    gameScheduleDataList: List<GameScheduleData>,
    navController: NavController = rememberNavController(),
    storyboardCarouselViewModel: StoryboardCarouselViewModel = viewModel()
) {
    val uiState by storyboardCarouselViewModel.uiState.collectAsState()
    val triviaText by storyboardCarouselViewModel.triviaText.collectAsState()
    val alpha: Float by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 5000),
        label = "alpha"
    )

    BaseBackground {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GamesListHeaderSection(team = team)
            GamesListSection(
                gameScheduleDataList = gameScheduleDataList,
                onGameClicked = { gameId ->
                    storyboardCarouselViewModel.fetchStoryboard(gameId)
                    navController.navigate("storyboard_carousel/$gameId")
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TeamGamesListScreenPreview() {
    TeamGamesListScreen(
        team = Team(
            119,
            "Los Angeles Dodgers",
            "Dodgers",
            "logoUrl"
        ),
        gameScheduleDataList = listOf(
            GameScheduleData(
                gameId = 775296,
                date = "Wed, Oct 30",
                team = "@ Yankees",
                imageUrl = "url_to_image1"
            ),
            GameScheduleData(
                gameId = 775294,
                date = "Tue, Oct 29",
                team = "@ Yankees",
                imageUrl = "url_to_image2"
            ),
            GameScheduleData(
                gameId = 775297,
                date = "Mon, Oct 28",
                team = "@ Yankees",
                imageUrl = "url_to_image3"
            )
        )
    )
}

