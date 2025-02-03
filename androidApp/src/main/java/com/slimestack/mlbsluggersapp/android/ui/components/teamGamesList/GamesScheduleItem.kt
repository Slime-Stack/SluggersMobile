package com.slimestack.mlbsluggersapp.android.ui.components.teamGamesList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.slimestack.mlbsluggersapp.android.R
import com.slimestack.mlbsluggersapp.data.models.GameScheduleData

@Composable
fun GameScheduleItem(data: GameScheduleData, onGameClicked: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onGameClicked(data.gameId) }
    ){
        AsyncImage(
            model = data.imageUrl,
            contentDescription = "${data.team} Logo",
            modifier = Modifier
                .fillMaxWidth()
                .height(144.dp),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.game_775296_story),
            error = painterResource(R.drawable.game_775296_story),
        )
        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier
            .fillMaxSize()
            .align(Alignment.BottomStart)
            .padding(16.dp),
        ){
            Column {
                Text(
                    text = data.date,
                    color = Color.White,
                    fontSize = 16.sp
                )
                Text(
                    text = data.team,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Icon(
                Icons.Filled.ArrowForward,
                contentDescription = "See More",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(30.dp)
            )
        }
    }
}
