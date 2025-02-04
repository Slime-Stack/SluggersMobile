package com.slimestack.mlbsluggersapp.android.ui.screens.teamGamesList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.slimestack.mlbsluggersapp.android.ui.components.baseComposables.BaseBackground
import com.slimestack.mlbsluggersapp.android.ui.components.teamGamesList.GamesListHeaderSection
import com.slimestack.mlbsluggersapp.android.ui.components.teamGamesList.GamesListSection
import com.slimestack.mlbsluggersapp.data.models.GameScheduleData
import com.slimestack.mlbsluggersapp.data.models.Team

@Composable
fun TeamGamesListScreen(
    team: Team,
    gameScheduleDataList: List<GameScheduleData>,
    navController: NavController = rememberNavController()
) {

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
                    navController.navigate("storyboard_carousel/$gameId&${team.teamId}")
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
            "logoUrl",
            "Los Angeles Dodgers",
            "Dodgers",
            119
        ),
        gameScheduleDataList = listOf(
            GameScheduleData(
                gamePk = 775296,
                date = "Wed, Oct 30",
                team = "@ Yankees",
                imageUrl = "url_to_image1"
            ),
            GameScheduleData(
                gamePk = 775294,
                date = "Tue, Oct 29",
                team = "@ Yankees",
                imageUrl = "url_to_image2"
            ),
            GameScheduleData(
                gamePk = 775297,
                date = "Mon, Oct 28",
                team = "@ Yankees",
                imageUrl = "url_to_image3"
            )
        )
    )
}

