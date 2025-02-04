package com.slimestack.mlbsluggersapp.android.ui.screens.getStarted

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.slimestack.mlbsluggersapp.android.R
import com.slimestack.mlbsluggersapp.android.ui.components.getStarted.GetStartedButton

@Composable
fun GetStartedScreen(
    onGetStartedClicked: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundImage: Painter = painterResource(id = R.drawable.splash_screen)
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = backgroundImage,
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, bottom = 88.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            GetStartedButton(
                onClick = onGetStartedClicked,
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GetStartedScreenPreview() {
    GetStartedScreen(onGetStartedClicked = {})
}
