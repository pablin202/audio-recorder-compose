package com.pdm.audiorecorder.data

import android.content.Context
import android.media.MediaPlayer
import android.media.audiofx.Visualizer
import androidx.core.net.toUri
import com.pdm.audiorecorder.domain.AudioPlayer
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.io.File
import javax.inject.Inject

class AndroidAudioPlayer @Inject constructor(
    private val context: Context
): AudioPlayer {

    private var player: MediaPlayer? = null

//    override fun playFile(file: File) {
//        MediaPlayer.create(context, file.toUri()).apply {
//            player = this
//            start()
//        }
//    }

    override fun playFile(file: File): Flow<ByteArray> = callbackFlow {
        player = MediaPlayer().apply {
            setDataSource(context, file.toUri())
            setOnPreparedListener {
                start()
            }
            prepareAsync()
        }

        val visualizer = Visualizer(player!!.audioSessionId)
        visualizer.captureSize = Visualizer.getCaptureSizeRange()[1]
        visualizer.setDataCaptureListener(object : Visualizer.OnDataCaptureListener {
            override fun onWaveFormDataCapture(
                visualizer: Visualizer?,
                waveform: ByteArray?,
                samplingRate: Int
            ) {
                waveform?.let {
                    trySend(it).isSuccess
                }
            }

            override fun onFftDataCapture(
                visualizer: Visualizer?,
                fft: ByteArray?,
                samplingRate: Int
            ) {
                // No necesitamos manejar FFT en este caso
            }
        }, Visualizer.getMaxCaptureRate() / 2, true, false)


        awaitClose {
            player?.stop()
            player?.release()
            player = null
            visualizer.enabled = false
            visualizer.release()
        }
    }


    override fun stop() {
        player?.stop()
        player?.release()
        player = null
    }
}