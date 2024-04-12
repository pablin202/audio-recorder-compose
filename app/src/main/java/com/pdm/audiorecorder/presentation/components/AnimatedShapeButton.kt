package com.pdm.audiorecorder.presentation.components

import android.provider.CalendarContract.Colors
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.pdm.audiorecorder.ui.theme.Red

@Composable
fun AnimatedShapeButton(
    mainSize: Dp = 70.dp,
    isRecording: Boolean,
    startRecording: () -> Unit,
    stopRecording: () -> Unit,
) {
    val cornerRadius =
        animateDpAsState(targetValue = if (!isRecording) 50.dp else 10.dp, label = "")
    val internalSize = animateDpAsState(
        targetValue = if (!isRecording) mainSize - 10.dp else mainSize - 30.dp,
        label = ""
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(16.dp)
            .size(mainSize)
            .clip(CircleShape)
            .background(Color.White)
            .padding(5.dp)
            .clickable {
                if (!isRecording) {
                    startRecording()
                } else {
                    stopRecording()
                }
            }
    ) {
        Box(
            modifier = Modifier
                .size(internalSize.value)
                .clip(RoundedCornerShape(cornerRadius.value))
                .background(Red)
        )
    }
}

@Preview
@Composable
fun AnimatedShapeButtonRecordingPreview() {
    AnimatedShapeButton(70.dp, true, {}) {

    }
}

@Preview
@Composable
fun AnimatedShapeButtonNoRecordingPreview() {
    AnimatedShapeButton(70.dp, false, {}) {

    }
}