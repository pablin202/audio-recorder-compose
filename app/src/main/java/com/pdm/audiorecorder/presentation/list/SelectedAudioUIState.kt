package com.pdm.audiorecorder.presentation.list

import com.pdm.audiorecorder.domain.models.AudioFile

data class SelectedAudioUIState(
    val showModal: Boolean = false,
    val audioFile: AudioFile = AudioFile(),
    val isPlaying: Boolean = false,
    val progress: Float = 0f,
    val currentTime: String = "00:00"
)
