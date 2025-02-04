package com.slimestack.mlbsluggersapp.android.ui.screens.selectTeam

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.slimestack.mlbsluggersapp.android.R
import com.slimestack.mlbsluggersapp.android.ui.components.baseComposables.BaseBackground
import com.slimestack.mlbsluggersapp.data.models.Team

@Composable
fun TeamSelectionScreen(teams: List<Team>, onTeamSelected: (Team) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image (fixed)
        Column(modifier = Modifier.fillMaxSize()) {
            // Header Frame (fixed)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Choose your",
                    style = TextStyle(
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight(500),
                        fontSize = 40.sp,
                        lineHeight = 60.sp,
                        textAlign = TextAlign.Left,
                    ),
                    color = Color.White
                )
                Text(
                    text = "Team",
                    style = TextStyle(
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight(600),
                        fontSize = 63.sp,
                        lineHeight = 94.5.sp,
                        textAlign = TextAlign.Left,
                    ),
                    color = Color.White
                )
            }
            // Grid of Tiles (scrollable)
            LazyVerticalGrid (
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                items(teams) { team ->
                    TeamTile(team = team, onTeamSelected = onTeamSelected)
                }
            }
        }
    }
}

@Composable
fun TeamTile(team: Team, onTeamSelected: (Team) -> Unit) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .aspectRatio(1f)
            .clickable { onTeamSelected(team) }
            .background(
                color = Color.White.copy(alpha = 0.9f),
                shape = RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
              AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(team.logoUrl)
                    .crossfade(true)
                    .build(),
                  placeholder = painterResource(R.drawable.ic_launcher),
                contentDescription = "${team.name} Logo",
                modifier = Modifier.size(50.dp),
                contentScale = ContentScale.Fit
             )
            Text(
                text = team.name,
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                ),
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TeamSelectionScreenPreview() {
    val teamsForPreview = listOf(
        Team("https://www.mlbstatic.com/team-logos/108.svg", "Los Angeles Angels", "Angels", 108),
        Team("https://www.mlbstatic.com/team-logos/109.svg", "Arizona Diamondbacks", "D-backs",  109),
        Team("https://www.mlbstatic.com/team-logos/119.svg", "Los Angeles Dodgers", "Dodgers", 119),
        Team("https://www.mlbstatic.com/team-logos/147.svg", "New York Yankees", "Yankees", 147),
    )
    BaseBackground {
        TeamSelectionScreen(teams = teamsForPreview, onTeamSelected = {})
    }
}
