package com.slimestack.mlbsluggersapp.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.slimestack.mlbsluggersapp.android.ui.components.baseComposables.BaseBackground
import com.slimestack.mlbsluggersapp.android.ui.screens.getStarted.GetStartedScreen
import com.slimestack.mlbsluggersapp.android.ui.screens.highlights.StoryboardCarouselScreen
import com.slimestack.mlbsluggersapp.android.ui.screens.highlights.StoryboardCarouselViewModel
import com.slimestack.mlbsluggersapp.android.ui.screens.loading.LoadingScreen
import com.slimestack.mlbsluggersapp.android.ui.screens.selectTeam.TeamSelectionScreen
import com.slimestack.mlbsluggersapp.android.ui.screens.selectTeam.TeamSelectionViewModel
import com.slimestack.mlbsluggersapp.data.models.GameScheduleData
import com.slimestack.mlbsluggersapp.android.ui.screens.teamGamesList.TeamGamesListScreen

@Composable
fun SluggersNavigation() {
    val navController = rememberNavController()
    val teamSelectionViewModel: TeamSelectionViewModel = viewModel()
    val teams by teamSelectionViewModel.teams.collectAsState()
    val storyBoardViewModel: StoryboardCarouselViewModel = viewModel()
    val uiState by storyBoardViewModel.uiState.collectAsState()

    NavHost(navController = navController, startDestination = "get_started") {
        composable("get_started") {
            BaseBackground {
                GetStartedScreen(onGetStartedClicked = {
                    navController.navigate("team_selection")
                })
            }
        }
        composable("team_selection") {
            BaseBackground {
                TeamSelectionScreen(teams = teams, onTeamSelected = {
                    navController.navigate("loading/${it.teamId}")
                })
            }
        }
        composable(
            route = "loading/{teamId}",
            arguments = listOf(navArgument("teamId") { type = NavType.IntType })
        ) { backStackEntry ->
            val teamId = backStackEntry.arguments?.getInt("teamId") ?: 0
            val team = teams.find { it.teamId == teamId }
            BaseBackground {
                team?.let { LoadingScreen(team = it, navController = navController) }
            }
        }
        composable(
            route = "team_games_list/{teamId}",
            arguments = listOf(navArgument("teamId") { type = NavType.IntType })
        ) { backStackEntry ->
            val teamId = backStackEntry.arguments?.getInt("teamId") ?: 0
            val team = teams.find { it.teamId == teamId }
            val gameScheduleDataList = listOf(
                GameScheduleData(
                    date = "Wed, Oct 30",
                    team = "@ Yankees",
                    imageUrl = "url_to_image1",
                    gameId = 775296
                ),
                GameScheduleData(
                    date = "Tue, Oct 29",
                    team = "@ Yankees",
                    imageUrl = "url_to_image2",
                    gameId = 775294
                ),
                GameScheduleData(
                    date = "Mon, Oct 28",
                    team = "@ Yankees",
                    imageUrl = "url_to_image3",
                    gameId = 775297
                )
            )
            BaseBackground {
                team?.let { TeamGamesListScreen(
                    team = team,
                    gameScheduleDataList = gameScheduleDataList,
                    navController = navController,
                    storyboardCarouselViewModel = storyBoardViewModel
                ) }
            }
        }
        composable(
            route = "storyboard_carousel/{gameId}",
            arguments = listOf(navArgument("gameId") { type = NavType.IntType })
        ) { backStackEntry ->
            val gameId = backStackEntry.arguments?.getInt("gameId") ?: 775296
            storyBoardViewModel.fetchStoryboard(gameId)
            StoryboardCarouselScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GetStartedScreenPreview() {
    GetStartedScreen(onGetStartedClicked = {
        // Handle the button click here
        println("Get Started button clicked!")
        // Navigate to the next screen, etc.
    })
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TeamSelectionScreenPreview() {
    val teamsViewModel: TeamSelectionViewModel = viewModel()
    val teams by teamsViewModel.teams.collectAsState()
    BaseBackground {
        TeamSelectionScreen(teams = teams, onTeamSelected = {
            println("Team selected: ${it.name}")
        })
    }
}
