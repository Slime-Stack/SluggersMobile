package com.slimestack.mlbsluggersapp.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.slimestack.mlbsluggersapp.android.R
import com.slimestack.mlbsluggersapp.android.ui.components.baseComposables.BaseBackground
import com.slimestack.mlbsluggersapp.android.ui.components.loading.RotatingImage
import com.slimestack.mlbsluggersapp.android.ui.screens.getStarted.GetStartedScreen
import com.slimestack.mlbsluggersapp.android.ui.screens.highlights.StoryboardCarouselScreen
import com.slimestack.mlbsluggersapp.android.ui.screens.highlights.StoryboardCarouselViewModel
import com.slimestack.mlbsluggersapp.android.ui.screens.loading.LoadingScreen
import com.slimestack.mlbsluggersapp.android.ui.screens.selectTeam.TeamSelectionScreen
import com.slimestack.mlbsluggersapp.android.ui.screens.selectTeam.TeamSelectionViewModel
import com.slimestack.mlbsluggersapp.android.ui.screens.teamGamesList.TeamGamesListScreen
import com.slimestack.mlbsluggersapp.android.ui.state.sealed.StoryboardCarouselUiState
import com.slimestack.mlbsluggersapp.data.models.GameScheduleData

@Composable
fun SluggersNavigation(splashScreen: SplashScreen) {
    val navController = rememberNavController()
    val teamSelectionViewModel: TeamSelectionViewModel = viewModel()
    val teams by teamSelectionViewModel.teams.collectAsState()
    val storyBoardViewModel: StoryboardCarouselViewModel = viewModel()
    val uiState by storyBoardViewModel.uiState.collectAsState()

    NavHost(navController = navController, startDestination = "get_started") {
            splashScreen.setKeepOnScreenCondition { teams.size == 30 }
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
                    gamePk = 775296
                ),
                GameScheduleData(
                    date = "Tue, Oct 29",
                    team = "@ Yankees",
                    imageUrl = "url_to_image2",
                    gamePk = 775294
                ),
                GameScheduleData(
                    date = "Mon, Oct 28",
                    team = "@ Yankees",
                    imageUrl = "url_to_image3",
                    gamePk = 775297
                )
            )
            BaseBackground {
                team?.let { TeamGamesListScreen(
                    team = team,
                    gameScheduleDataList = gameScheduleDataList,
                    navController = navController
                ) }
            }
        }
        composable(
            route = "storyboard_carousel/{gameId}&{teamId}",
            arguments = listOf(
                navArgument("teamId") { type = NavType.IntType },
                navArgument("gameId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val gameId = backStackEntry.arguments?.getInt("gameId") ?: 0
            val teamId = backStackEntry.arguments?.getInt("teamId") ?: 0

            storyBoardViewModel.fetchStoryboard(gameId, teamId)
            when (uiState) {
                is StoryboardCarouselUiState.Loading -> {
                    // Show a loading indicator
                    BaseBackground {
                        RotatingImage(painter = painterResource(R.drawable.baseball_icon))
                    }
                    println("Loading...")
                }
                is StoryboardCarouselUiState.Success -> {
                    // Display the data
                    val storyboard = (uiState as StoryboardCarouselUiState.Success).storyboard
                    StoryboardCarouselScreen(
                        storyboard,
                        onNavigateBack = { navController.popBackStack() }
                    )
                    println("Success! Displaying ${storyboard.storyTitle} storyboard")
                }
                is StoryboardCarouselUiState.Error -> {
                    // Show an error message
                    println("Error: ${(uiState as StoryboardCarouselUiState.Error).message}")
                }
            }
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
