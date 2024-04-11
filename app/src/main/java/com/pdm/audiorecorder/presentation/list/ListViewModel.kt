package com.pdm.audiorecorder.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm.audiorecorder.domain.AudioPlayer
import com.pdm.audiorecorder.domain.AudioVisualizer
import com.pdm.audiorecorder.domain.FileManager
import com.pdm.audiorecorder.presentation.home.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val audioPlayer: AudioPlayer,
    private val audioVisualizer: AudioVisualizer,
    private val fileManager: FileManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<ListUIState>(ListUIState.Loading)
    val uiState: StateFlow<ListUIState> = _uiState

    init {
        listAudioFiles()
    }

    private fun listAudioFiles() {
        viewModelScope.launch {
            _uiState.value = ListUIState.AudioFilesListed(fileManager.listAudioFiles())
        }
    }
}