package com.pdm.audiorecorder.presentation.list

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.pdm.audiorecorder.R
import com.pdm.audiorecorder.presentation.list.components.AudioPlayerItem
import com.pdm.audiorecorder.ui.theme.Grey
import kotlinx.coroutines.launch

@Composable
fun ListScreen(viewModel: ListViewModel = hiltViewModel()) {

    val state by viewModel.uiState.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = keyboardController) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is ListScreenEvent.OnCompletion -> {
                    scope.launch {
                        keyboardController?.hide()
                    }
                }
            }
        }
    }

    ListContent(state, {
        viewModel.favouriteChanged(it)
    }) {
        viewModel.playAudio(it)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListContent(
    state: ListUIState,
    onFavouriteChanged: (Int) -> Unit,
    onAudioSelected: (Int) -> Unit
) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(
                text = stringResource(id = R.string.recordings), color = Grey,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
            )
        })
    }) { it ->
        if (state.loading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = it.calculateTopPadding(),
                        bottom = it.calculateBottomPadding()
                    )
            ) {
                LazyColumn {
                    items(state.files, key = { it.id }) { recording ->
                        AudioPlayerItem(
                            audioFile = recording,
                            { onFavouriteChanged(it) },
                            { onAudioSelected(it) })
                    }
                }
            }
        }
    }
}
