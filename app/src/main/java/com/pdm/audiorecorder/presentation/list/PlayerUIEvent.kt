package com.pdm.audiorecorder.presentation.list

sealed interface PlayerUIEvent {
    data class PlayAudio(val audioId: Int) : PlayerUIEvent
    data object ResumeAudio : PlayerUIEvent
    data object PauseAudio : PlayerUIEvent
    data object StopAudio : PlayerUIEvent
    data class SeekTo(val position: Float) : PlayerUIEvent
}