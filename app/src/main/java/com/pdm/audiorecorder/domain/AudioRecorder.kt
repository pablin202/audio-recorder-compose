package com.pdm.audiorecorder.domain

import kotlinx.coroutines.flow.Flow
import java.io.File

interface AudioRecorder {
    fun start(output: File): Flow<ByteArray>
    fun stop()
}