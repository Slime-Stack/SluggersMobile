package com.slimestack.mlbsluggersapp.android.ui.screens.loading

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.slimestack.mlbsluggersapp.android.ui.state.sealed.TeamDetailsUiState
import com.slimestack.mlbsluggersapp.android.ui.components.baseComposables.BaseBackground
import com.slimestack.mlbsluggersapp.android.ui.components.loading.LoadingCard
import com.slimestack.mlbsluggersapp.data.models.Team

@Composable
fun LoadingScreen(
    team: Team,
    navController: NavController,
    viewModel: LoadingScreenViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val triviaText by viewModel.triviaText.collectAsState()

    val alpha: Float by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 5000),
        label = "alpha"
    )

    LaunchedEffect(key1 = team) {
        viewModel.fetchTeamDetails(team.teamId)
    }

    when (uiState) {
        is TeamDetailsUiState.Loading -> {
            BaseBackground {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 144.dp)
                    ,
                    verticalArrangement = Arrangement.Center
                ) {
                    LoadingCard(
                        team = team,
                        triviaText = triviaText,
                        alpha = alpha
                    )
                }
            }
        }

        is TeamDetailsUiState.Success -> {
            val teamDetails = (uiState as TeamDetailsUiState.Success).teamDetails
            if (teamDetails.isNotBlank()) {
                LaunchedEffect(key1 = teamDetails) {
                    navController.navigate("team_games_list/${team.teamId}")
                }
            } else {
                Text(
                    text = "No team details available",
                    color = Color.White
                )
            }
        }

        is TeamDetailsUiState.Error -> {
            val errorMessage = (uiState as TeamDetailsUiState.Error).message
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Error: $errorMessage")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    LoadingScreen(
        team = Team(
            "logoUrl",
            "Los Angeles Dodgers",
            "Dodgers",
            119
        ),
        navController = NavController(LocalContext.current)
    )
}
