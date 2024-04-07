package com.pdm.audiorecorder.presentation

import androidx.lifecycle.ViewModel
import com.pdm.audiorecorder.domain.AudioPlayer
import com.pdm.audiorecorder.domain.AudioRecorder
import com.pdm.audiorecorder.domain.FileManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.File
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val audioRecorder: AudioRecorder,
    private val audioPlayer: AudioPlayer,
    private val fileManager: FileManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState: StateFlow<UIState> = _uiState

    init {
        listAudioFiles()
    }

    fun startAudioRecording(): Flow<ByteArray> {
        val outputFile = createNewAudioFile()
        _uiState.value = UIState.AudioRecordingStarted
        return audioRecorder.start(outputFile)
    }

    fun stopAudioRecording() {
        audioRecorder.stop()
        _uiState.value = UIState.AudioRecordingStopped
        listAudioFiles()
    }

    fun stopAudioPlayback() {
        audioPlayer.stop()
        _uiState.value = UIState.AudioPlaybackStopped
    }

    private fun createNewAudioFile(): File {
        return File(fileManager.createNewAudioFile())
    }

    private fun listAudioFiles() {
        _uiState.value = UIState.AudioFilesListed(fileManager.listAudioFiles())
    }

    fun renameAudioFile(oldName: String, newName: String) {
        val success = fileManager.renameAudioFile(oldName, newName)
        if (success) {
            _uiState.value = UIState.AudioFileRenamed(oldName, newName)
        } else {
            _uiState.value = UIState.Error("Failed to rename file")
        }
    }
}