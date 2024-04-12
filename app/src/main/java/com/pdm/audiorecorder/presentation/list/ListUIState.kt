package com.pdm.audiorecorder.presentation.list

import com.pdm.audiorecorder.domain.models.AudioFile

data class ListUIState(
    val loading: Boolean = false,
    val files: List<AudioFile> = emptyList(),
    val error: Throwable? = null
)