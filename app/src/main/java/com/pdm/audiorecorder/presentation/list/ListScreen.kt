package com.pdm.audiorecorder.presentation.list

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pdm.audiorecorder.presentation.list.components.AudioPlayerItem
import com.pdm.audiorecorder.ui.theme.Grey
import com.pdm.audiorecorder.R

@Composable
fun ListScreen(viewModel: ListViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()
    ListContent(state)
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListContent(
    state: ListUIState
) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(
                text = stringResource(id = R.string.recordings), color = Grey,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
            )
        })
    }) { _ ->
        when (state) {
            is ListUIState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is ListUIState.AudioFilesListed -> {
                LazyColumn {
                    items(state.audioFiles) { recording ->
                        AudioPlayerItem(audioName = recording.name,
                            audioDurationMs = recording.duration,
                            onAudioSelected = {
                                //if (it) startReproduction() else pauseReproduction()
                            })
                    }
                }
            }

            is ListUIState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                }
            }
        }
    }
}