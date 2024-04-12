package com.pdm.audiorecorder.presentation.list

import com.pdm.audiorecorder.domain.models.AudioFile

sealed class ListScreenEvent {
    data class OnCompletion(val audioFile: AudioFile) : ListScreenEvent()
}