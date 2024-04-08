package com.pdm.audiorecorder.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pdm.audiorecorder.presentation.components.AnimatedCircleToRoundedRectangleWithPadding
import com.pdm.audiorecorder.presentation.components.AudioVisualizer
import kotlinx.coroutines.flow.MutableSharedFlow

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val waveform = viewModel.visualizerData.collectAsState(initial = null).value

    val recordings by viewModel.recordings.collectAsState()

    HomeContent(files = recordings, waveform = waveform, startRecording = { viewModel.startAudioRecording() }) {
        viewModel.stopAudioRecording()
    }
}

@Composable
fun HomeContent(
    files: List<String>,
    waveform: ByteArray?,
    startRecording: () -> Unit,
    stopRecording: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

         LazyColumn{
            items(files) { recording ->
                Text(text = recording)
            }
        }

//        AnimatedCircleToRoundedRectangleWithPadding(size = 50.dp) {
//            if (it) startRecording() else stopRecording()
//        }

        Spacer(modifier = Modifier.height(15.dp))

//        Canvas(modifier = Modifier.fillMaxWidth().height(100.dp)) {
//            waveform?.let { data ->
//                val step = size.width / data.size
//                for (i in data.indices) {
//                    val x = i * step
//                    val y = data[i] / 255f * size.height
//                    drawLine(
//                        start = Offset(x, size.height / 2 - y / 2),
//                        end = Offset(x, size.height / 2 + y / 2),
//                        color = Color.Red,
//                        strokeWidth = Stroke.DefaultMiter
//                    )
//                }
//            }
//        }

//        AudioVisualizer(flow = flow)
    }
}

@Preview
@Composable
fun HomePreviewContent() {

}

