package com.pdm.audiorecorder.domain

import java.io.File

interface AudioPlayer {
    fun playFile(file: File, onCompletion: () -> Unit)
    fun playFile(file: String, onCompletion: () -> Unit)
    fun stop()
    fun pause()
    fun getAudioSessionId(): Int?
}