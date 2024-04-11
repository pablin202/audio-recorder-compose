package com.pdm.audiorecorder.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm.audiorecorder.domain.AudioPlayer
import com.pdm.audiorecorder.domain.AudioRecorder
import com.pdm.audiorecorder.domain.AudioVisualizer
import com.pdm.audiorecorder.domain.FileManager
import com.pdm.audiorecorder.domain.models.AudioFile
import dagger.hilt.android.lifecycle.HiltViewModel
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


    private val _visualizerData = MutableStateFlow<ByteArray?>(null)
    val visualizerData: StateFlow<ByteArray?> = _visualizerData

    private val _isRecording = MutableStateFlow(false)
    val isRecording: StateFlow<Boolean> = _isRecording

    private val _amplitudes = MutableStateFlow<List<Int>>(emptyList())
    val amplitudes: StateFlow<List<Int>> = _amplitudes

    fun startAudioRecording() {
        val outputFile = createNewAudioFile()
        _isRecording.value = true
        viewModelScope.launch {
            audioRecorder.start(outputFile).collect {maxAmplitude ->
                val newList = _amplitudes.value.toMutableList()
                if (newList.size >= MAX_AMPLITUDES) {
                    newList.removeFirst()
                }
                newList.add(maxAmplitude)
                _amplitudes.value = newList
            }
        }
    }

    fun stopAudioRecording() {
        audioRecorder.stop()
        _isRecording.value = false
        _uiState.value = UIState.AudioRecordingStopped
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

    fun pauseAudioPlayback() {
        audioPlayer.pause()
        _uiState.value = UIState.AudioPlaybackPaused
    }

    private fun createNewAudioFile(): File {
        return File(fileManager.createNewAudioFile())
    }

    fun renameAudioFile(oldName: String, newName: String) {
        val success = fileManager.renameAudioFile(oldName, newName)
        if (success) {
            _uiState.value = UIState.AudioFileRenamed(oldName, newName)
        } else {
            _uiState.value = UIState.Error("Failed to rename file")
        }
    }

    companion object {
        private const val MAX_AMPLITUDES = 50 // Ajusta este valor según sea necesario
    }
}