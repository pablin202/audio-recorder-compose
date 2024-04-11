package com.pdm.audiorecorder.presentation.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pdm.audiorecorder.domain.models.AudioFile
import com.pdm.audiorecorder.presentation.components.AnimatedShapeButton

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    val waveform = viewModel.visualizerData.collectAsState(initial = null).value
    val isRecording by viewModel.isRecording.collectAsState()
    val amplitudes by viewModel.amplitudes.collectAsState()

    HomeContent(
        waveform = waveform,
        isRecording = isRecording,
        amplitudes = amplitudes,
        startRecording = { viewModel.startAudioRecording() },
        stopRecording = { viewModel.stopAudioRecording() },
        startReproduction = { /*TODO*/ }) {
    }

}

@Composable
fun HomeContent(
    waveform: ByteArray?,
    isRecording: Boolean,
    amplitudes: List<Int>,
    startRecording: () -> Unit,
    stopRecording: () -> Unit,
    startReproduction: () -> Unit,
    pauseReproduction: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AudioVisualizer(amplitudes)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedShapeButton(
                isRecording = isRecording, startRecording = {
                    startRecording()
                }
            ) {
                stopRecording()
            }
        }
    }
}


@Composable
fun AudioVisualizer(amplitudes: List<Int>) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val totalBars = amplitudes.size
        amplitudes.forEachIndexed { index, amplitude ->
            drawAmplitudeBar(amplitude, index, totalBars, 32767)
        }
    }
}

private fun DrawScope.drawAmplitudeBar(
    amplitude: Int,
    index: Int,
    totalBars: Int,
    maxAmplitude: Int
) {

    val normalizedAmplitude = amplitude.toFloat() / maxAmplitude
    val barHeight = size.height * 0.5f * normalizedAmplitude
    val totalWidth = size.width
    val barWidth = totalWidth / totalBars // Ajustamos el ancho de la barra para que todas las barras quepan en el ancho total
    val effectiveBarWidth = barWidth * 0.8f // Usamos el 80% del ancho de la barra para la barra en sí y el 20% para el espaciado
    val spacing = barWidth * 0.2f // Calculamos el espaciado como el 20% del ancho de la barra
    val barTop = (size.height / 2) - (barHeight / 2)

    val barStartX = totalWidth - effectiveBarWidth - (index * barWidth) - (spacing * index)

    drawRoundRect(
        color = Color.Red,
        topLeft = Offset(x = barStartX, y = barTop),
        size = Size(width = effectiveBarWidth, height = barHeight),
        cornerRadius = CornerRadius(x = effectiveBarWidth / 2, y = effectiveBarWidth / 2)
    )
}

@Preview
@Composable
fun HomePreviewContent() {

}

