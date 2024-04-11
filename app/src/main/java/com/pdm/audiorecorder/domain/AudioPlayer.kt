package com.pdm.audiorecorder.domain

import kotlinx.coroutines.flow.Flow
import java.io.File

interface AudioPlayer {
    fun playFile(file: File)
    fun stop()
    fun pause()
    fun getAudioSessionId(): Int?
}