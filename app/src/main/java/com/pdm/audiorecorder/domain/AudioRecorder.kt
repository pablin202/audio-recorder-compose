package com.pdm.audiorecorder.domain

import android.media.audiofx.Visualizer
import kotlinx.coroutines.flow.Flow
import java.io.File

interface AudioRecorder {
    fun start(outputFile: File): Flow<Int>
    fun stop(onNewRecord: (file: File) -> Unit)
}