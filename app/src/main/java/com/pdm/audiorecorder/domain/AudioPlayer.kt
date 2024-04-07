package com.pdm.audiorecorder.domain

import kotlinx.coroutines.flow.Flow
import java.io.File

interface AudioPlayer {
    fun playFile(file: File): Flow<ByteArray>
    fun stop()
}