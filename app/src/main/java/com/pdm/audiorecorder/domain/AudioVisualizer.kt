package com.pdm.audiorecorder.domain

interface AudioVisualizer {
    fun attachSession(sessionId: Int, updateVisualData: (ByteArray) -> Unit)
    fun detachSession()
}