package com.slimestack.mlbsluggersapp.android.ui.components.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slimestack.mlbsluggersapp.android.R
import com.slimestack.mlbsluggersapp.data.models.Team

@Composable
fun LoadingCard(
    team: Team,
    triviaText: String,
    alpha: Float,
) {
    Box(
        modifier = Modifier
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(vertical = 88.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            // Loading Image
            RotatingImage(painterResource(R.drawable.baseball_icon))
            Spacer(modifier = Modifier.height(24.dp))
            // Title
            Text(
                text = "Get ready, ${team.shortName} Fan!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()

            )
            Spacer(modifier = Modifier.height(8.dp))

            // Subtitle
            Text(
                text = "We're loading up some exciting highlights!",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // "Did You Know" Section
            Text(
                text = "DID YOU KNOW?",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()

            )
            Text(
                modifier = Modifier
                    .padding(top = 4.dp, bottom = 16.dp)
                    .alpha(alpha),
                text = triviaText,
                fontSize = 14.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingCardPreview() {
    LoadingCard(
        team = Team(
            "logoUrl",
            "Los Angeles Dodgers",
            "Dodgers",
            119
        ),
        triviaText = "This is a sample trivia text",
        alpha = 1f
    )
}
