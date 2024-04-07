package com.pdm.audiorecorder.presentation

import android.content.Context
import android.media.MediaPlayer
import android.media.audiofx.Visualizer
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import kotlin.math.abs

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        when (val state = uiState) {
            is UIState.Loading -> {
                CircularProgressIndicator()
            }
            is UIState.AudioRecordingStarted -> {
                Canvas(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    // DrawAudioWaveform(waveform = state.array, color = Color.Red)
                }
            }
            is UIState.AudioPlaybackStarted -> {
            }
            is UIState.AudioFilesListed -> {
            }
            is UIState.StopAudioRecording -> {

            }
            is UIState.StopAudioPlayback -> {
                // Aquí puedes añadir la lógica para manejar el estado de detención de la reproducción de audio
            }
            else -> {
                Text("Error occurred")
            }
        }
    }
}

@Composable
private fun DrawScope.DrawAudioWaveform(waveform: ByteArray, color: Color) {
    val width = size.width
    val height = size.height
    val numPoints = waveform.size
    val stepX = width / (numPoints - 1)

    drawIntoCanvas { canvas ->
        val path = android.graphics.Path()
        waveform.forEachIndexed { index, value ->
            val x = index * stepX
            val y = height * (1 - (abs(value.toDouble()) / Byte.MAX_VALUE))
            if (index == 0) {
                path.moveTo(x, y.toFloat())
            } else {
                path.lineTo(x, y.toFloat())
            }
        }

        canvas.nativeCanvas.drawPath(path, android.graphics.Paint().apply {
            strokeWidth = 3.dp.toPx()
            style = android.graphics.Paint.Style.STROKE
        })
    }
}

