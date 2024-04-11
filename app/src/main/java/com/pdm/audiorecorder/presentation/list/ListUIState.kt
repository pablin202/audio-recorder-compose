package com.pdm.audiorecorder.presentation.list

import com.pdm.audiorecorder.domain.models.AudioFile
import com.pdm.audiorecorder.presentation.home.UIState

sealed class ListUIState {
    data object Loading : ListUIState()
    data class AudioFilesListed(
        val audioFiles: List<AudioFile>,
        val isPlaying: Boolean = false,
        val currentAudioName: String? = null
    ) : ListUIState()

    data class Error(val message: String) : ListUIState()
}