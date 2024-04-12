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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
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

    fun playAudio(audioId: Int) {
        _files.value.find { it.id == audioId }?.let {
            audioPlayer.playFile(it.path) {
                viewModelScope.launch {
                    _uiEvent.send(ListScreenEvent.OnCompletion(it))
                }
            }
        }
    }

    fun pauseAudio() {
        audioPlayer.pause()
    }

    fun stopAudio() {
        audioPlayer.stop()
    }

    fun favouriteChanged(audioId: Int) {
        _files.value.find { it.id == audioId }?.let {
            viewModelScope.launch {
                audioRepository.updateAudio(it.copy(isFavorite = !it.isFavorite))
            }
        }
    }
}