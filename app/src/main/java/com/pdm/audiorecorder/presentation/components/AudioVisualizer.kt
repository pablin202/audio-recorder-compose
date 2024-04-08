package com.pdm.audiorecorder.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableSharedFlow

@Composable
fun AudioVisualizer(flow: MutableSharedFlow<ByteArray?>) {
    val waveform = remember { mutableStateOf(byteArrayOf()) }

    LaunchedEffect(flow) {
        flow.collect { bytes ->
            if (bytes != null) {
                waveform.value = bytes
            }
        }
    }

    Canvas(modifier = androidx.compose.ui.Modifier
        .fillMaxWidth()
        .height(200.dp)) {
        val path = Path()
        waveform.value.forEachIndexed { index, byte ->
            val x = index * (size.width / waveform.value.size)
            val y = byte * size.height / Byte.MAX_VALUE
            if (index == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }
        }
        drawPath(path, color = Color.Red)
    }
}