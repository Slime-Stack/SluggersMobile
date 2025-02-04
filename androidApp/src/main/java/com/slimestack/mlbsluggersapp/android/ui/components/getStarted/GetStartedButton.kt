package com.slimestack.mlbsluggersapp.android.ui.components.getStarted

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.slimestack.mlbsluggersapp.android.R
import com.slimestack.mlbsluggersapp.android.ui.components.baseComposables.BaseButton


@Composable
fun GetStartedButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    BaseButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp),
        textStringResource = R.string.get_started,
        shape = RoundedCornerShape(16.dp),
        containerColor = Color.White,
        contentColor = Color.Black,
        showArrow = true,
    )
}

@Preview(showBackground = true)
@Composable
fun GetStartedButtonPreview() {
    GetStartedButton(onClick = {},)
}
