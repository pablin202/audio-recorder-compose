package com.pdm.audiorecorder.data

import android.media.audiofx.Visualizer
import com.pdm.audiorecorder.domain.AudioVisualizer

class AudioVisualizerImpl : AudioVisualizer {

    private var visualizer: Visualizer? = null
    override fun attachSession(sessionId: Int, updateVisualData: (ByteArray) -> Unit) {
        if (sessionId < 0) {
            return
        }

        visualizer?.release()
        visualizer = Visualizer(sessionId).apply {
            captureSize = Visualizer.getCaptureSizeRange()[1]
            setDataCaptureListener(object : Visualizer.OnDataCaptureListener {
                override fun onWaveFormDataCapture(visualizer: Visualizer?, waveform: ByteArray?, samplingRate: Int) {
                    updateVisualData(waveform ?: ByteArray(0))
                }

                override fun onFftDataCapture(visualizer: Visualizer?, fft: ByteArray?, samplingRate: Int) {

                }
            }, Visualizer.getMaxCaptureRate() / 2, true, false)
            enabled = true
        }
    }

    override fun detachSession() {
        visualizer?.enabled = false
        visualizer?.release()
        visualizer = null
    }
}