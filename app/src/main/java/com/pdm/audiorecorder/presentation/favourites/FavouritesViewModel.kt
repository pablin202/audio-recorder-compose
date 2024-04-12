package com.pdm.audiorecorder.presentation.favourites

import androidx.lifecycle.ViewModel
import com.pdm.audiorecorder.domain.AudioPlayer
import com.pdm.audiorecorder.domain.AudioVisualizer
import com.pdm.audiorecorder.domain.FileManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val audioPlayer: AudioPlayer,
    private val audioVisualizer: AudioVisualizer,
    private val fileManager: FileManager,
) : ViewModel() {

}