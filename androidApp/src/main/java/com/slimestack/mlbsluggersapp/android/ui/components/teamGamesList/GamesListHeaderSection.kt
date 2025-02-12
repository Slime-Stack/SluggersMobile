package com.slimestack.mlbsluggersapp.android.ui.components.teamGamesList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.slimestack.mlbsluggersapp.android.R
import com.slimestack.mlbsluggersapp.data.models.Team

@Composable
fun GamesListHeaderSection(team: Team) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = stringResource(R.string.games_list_hey_there),
                fontSize = 20.sp,
                color = Color.Gray
            )
            Text(
                text = stringResource(R.string.short_name_fan, team.shortName),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Box {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(team.logoUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_launcher),
                contentDescription = stringResource(R.string.selected_team_name, team.name),
                modifier = Modifier.size(48.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
    Text(
        text = stringResource(R.string.recent_highlights_label),
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 5.dp)
    )
    Text(
        text = stringResource(R.string.description_see_the_latest_action),
        fontSize = 16.sp,
        color = Color.Gray,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    )
}
