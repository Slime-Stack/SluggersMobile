package com.slimestack.mlbsluggersapp.android.ui.components.teamGamesList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.slimestack.mlbsluggersapp.data.models.GameScheduleData

@Composable
fun GamesListSection(gameScheduleDataList: List<GameScheduleData>, onGameClicked: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(top = 8.dp)
    ) {
        gameScheduleDataList.forEach { data ->
            GameScheduleItem(data, onGameClicked)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
