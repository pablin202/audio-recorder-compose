package com.pdm.audiorecorder.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm.audiorecorder.domain.AudioPlayer
import com.pdm.audiorecorder.domain.AudioVisualizer
import com.pdm.audiorecorder.domain.FileManager
import com.pdm.audiorecorder.domain.models.AudioFile
import com.pdm.audiorecorder.domain.repositories.AudioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val audioPlayer: AudioPlayer,
    private val audioVisualizer: AudioVisualizer,
    private val fileManager: FileManager,
    private val audioRepository: AudioRepository
) : ViewModel() {

    private val _loadingState = MutableStateFlow(false)

    private val _error: MutableStateFlow<Throwable?> =
        MutableStateFlow(null)

    private val _files: MutableStateFlow<List<AudioFile>> = MutableStateFlow(emptyList())

    private val _uiEvent = Channel<ListScreenEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _audioState = MutableStateFlow(SelectedAudioUIState())
    val audioState: StateFlow<SelectedAudioUIState> = _audioState

    init {
        // Collect the Flow<List<AudioFile>> from the repository and update _files.
        viewModelScope.launch {
            audioRepository.getAllAudios.collect { listOfAudioFiles ->
                _files.value = listOfAudioFiles
            }
        }
    }

    val uiState: StateFlow<ListUIState> = combine(
        _loadingState, _error, _files
    ) { isLoading, error, files ->
        ListUIState(
            loading = isLoading,
            files = files,
            error = error
        )
    }.catch {
        ListUIState(
            error = it
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ListUIState(loading = true)
    )

    fun onEvent(event: PlayerUIEvent) {
        when (event) {
            PlayerUIEvent.PauseAudio -> {
                pauseAudio()
            }

            PlayerUIEvent.ResumeAudio -> {
                resumeAudio()
            }

            PlayerUIEvent.StopAudio -> {
                stopAudio()
            }

            is PlayerUIEvent.PlayAudio -> {
                playAudio(event.audioId)
            }

            is PlayerUIEvent.SeekTo -> {
                seekTo(event.position)
            }
        }
    }

    private fun playAudio(audioId: Int) {
        _files.value.find { it.id == audioId }?.let { audioFile ->
            audioPlayer.playFile(audioFile.path) {
                viewModelScope.launch {
                    _uiEvent.send(ListScreenEvent.OnCompletion(audioFile))
                }
            }
            _audioState.update {
                it.copy(
                    showModal = true,
                    audioFile =
                    audioFile,
                    isPlaying = true
                )
            }
            updateProgress()
        }
    }

    private fun resumeAudio() {
        audioPlayer.resume()
        _audioState.update {
            it.copy(
                isPlaying = true
            )
        }
        updateProgress()
    }

    private fun pauseAudio() {
        _audioState.update {
            it.copy(
                isPlaying = false
            )
        }
        audioPlayer.pause()
    }

    private fun stopAudio() {
        _audioState.update {
            SelectedAudioUIState()
        }
        audioPlayer.stop()
    }

    private fun seekTo(progress: Float) {
        if (_audioState.value.showModal) {
            val seekPosition = (_audioState.value.audioFile.duration * progress).toInt()
            audioPlayer.seekTo(seekPosition)
            _audioState.update {
                it.copy(
                    progress = progress,
                    currentTime = String.format(
                        "%02d:%02d",
                        (seekPosition / 1000) / 60,
                        (seekPosition / 1000) % 60
                    )
                )
            }
        }
    }

    private fun updateProgress() {
        viewModelScope.launch {
            while (_audioState.value.isPlaying) {
                val currentPos = audioPlayer.getCurrentPosition()
                _audioState.update {
                    it.copy(
                        progress = currentPos / _audioState.value.audioFile.duration.toFloat(),
                        currentTime = String.format(
                            "%02d:%02d",
                            (currentPos / 1000) / 60,
                            (currentPos / 1000) % 60
                        )
                    )
                }
                delay(1000)
            }
        }
    }

    fun favouriteChanged(audioId: Int) {
        _files.value.find { it.id == audioId }?.let {
            viewModelScope.launch {
                audioRepository.updateAudio(it.copy(isFavorite = !it.isFavorite))
            }
        }
    }
}