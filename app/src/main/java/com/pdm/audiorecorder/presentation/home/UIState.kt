package com.pdm.audiorecorder.presentation.home

sealed class UIState {
    data object Loading : UIState()
    data object AudioRecordingStarted : UIState()
    data object AudioRecordingStopped : UIState()
    data class Error(val message: String) : UIState()
}
