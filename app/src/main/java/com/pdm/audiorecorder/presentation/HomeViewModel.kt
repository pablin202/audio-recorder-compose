package com.pdm.audiorecorder.presentation

import android.media.audiofx.Visualizer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm.audiorecorder.domain.AudioPlayer
import com.pdm.audiorecorder.domain.AudioRecorder
import com.pdm.audiorecorder.domain.AudioVisualizer
import com.pdm.audiorecorder.domain.FileManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val audioRecorder: AudioRecorder,
    private val audioPlayer: AudioPlayer,
    private val audioVisualizer: AudioVisualizer,
    private val fileManager: FileManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState: StateFlow<UIState> = _uiState

    private val _recordings = MutableStateFlow<List<String>>(emptyList())
    val recordings: StateFlow<List<String>> = _recordings

    private val _visualizerData = MutableStateFlow<ByteArray?>(null)
    val visualizerData: StateFlow<ByteArray?> = _visualizerData

    init {
        listAudioFiles()
    }

    fun startAudioRecording() {
        val outputFile = createNewAudioFile()
        audioRecorder.start(outputFile)
    }

    fun stopAudioRecording() {
        audioRecorder.stop()
        _uiState.value = UIState.AudioRecordingStopped
        listAudioFiles()
    }

    fun playAudio(fileName: String) {
        audioPlayer.playFile(fileManager.getFile(fileName)).also {
            audioPlayer.getAudioSessionId()?.let {
                audioVisualizer.attachSession(it) { waveform ->
                    _visualizerData.value = waveform
                }
            }
        }
    }

    fun stopAudioPlayback() {
        audioPlayer.stop()
        _uiState.value = UIState.AudioPlaybackStopped
    }

    private fun createNewAudioFile(): File {
        return File(fileManager.createNewAudioFile())
    }

    private fun listAudioFiles() {
        viewModelScope.launch {
            _recordings.value = fileManager.listAudioFiles()
        }
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