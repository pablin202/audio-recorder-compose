package com.pdm.audiorecorder.presentation.home

sealed class UIState {
    data object Loading : UIState()
    data class AudioFilesListed(val audioFiles: List<String>) : UIState()
    data class AudioFileRenamed(val oldName: String,
                                val newName: String) : UIState()

    data object AudioRecordingStarted : UIState()
    data object AudioRecordingStopped : UIState()
    data class AudioPlaybackStarted(val filePath: String) : UIState()
    data object AudioPlaybackStopped : UIState()
    data object AudioPlaybackPaused : UIState()
    data object StopAudioRecording : UIState()
    data object StopAudioPlayback : UIState()
    data class Error(val message: String) : UIState()
}
