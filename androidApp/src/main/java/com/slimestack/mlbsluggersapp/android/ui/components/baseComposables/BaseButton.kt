package com.slimestack.mlbsluggersapp.android.ui.components.baseComposables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slimestack.mlbsluggersapp.android.R

@Composable
fun BaseButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textStringResource: Int,
    shape: Shape = RoundedCornerShape(16.dp),
    containerColor: Color,
    contentColor: Color,
    showArrow: Boolean = false
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        contentPadding = PaddingValues(16.dp),
        shape = shape
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(textStringResource),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            if (showArrow) {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = stringResource(textStringResource),
                    tint = contentColor
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BaseButtonPreview() {
    BaseButton(
        onClick = {},
        textStringResource = R.string.get_started, containerColor = Color.White,
        contentColor = Color.Black,
        showArrow = true
    )
}

@Preview(showBackground = true)
@Composable
fun BaseButtonPreviewNoArrow() {
    BaseButton(
        onClick = {},
        textStringResource = R.string.get_started, containerColor = Color.White,
        contentColor = Color.Black,
        showArrow = false
    )
}
