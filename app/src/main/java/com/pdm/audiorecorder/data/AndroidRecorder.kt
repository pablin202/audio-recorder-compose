package com.pdm.audiorecorder.data

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import com.pdm.audiorecorder.domain.AudioRecorder
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import android.media.audiofx.Visualizer
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class AndroidRecorder @Inject constructor(private val context: Context) : AudioRecorder {

    private var recorder: MediaRecorder? = null

    private fun createRecorder(): MediaRecorder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else MediaRecorder()
    }

//    override fun start(output: File) {
//        createRecorder().apply {
//            setAudioSource(MediaRecorder.AudioSource.MIC)
//            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
//            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
//            setOutputFile(FileOutputStream(output).fd)
//            prepare()
//            start()
//            recorder = this
//        }
//    }

    override fun start(output: File): Flow<ByteArray> = callbackFlow {
        recorder = createRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(output)
            prepare()
            start()
        }

        val visualizer = Visualizer(0)
        visualizer.captureSize = Visualizer.getCaptureSizeRange()[1]
        visualizer.setDataCaptureListener(object : Visualizer.OnDataCaptureListener {
            override fun onWaveFormDataCapture(
                visualizer: Visualizer?,
                waveform: ByteArray?,
                samplingRate: Int
            ) {
                waveform?.let { trySend(it).isSuccess }
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
            recorder?.stop()
            recorder?.release()
            visualizer.enabled = false
            visualizer.release()
        }
    }

    override fun stop() {
        recorder?.stop();
        recorder?.reset();   // You can reuse the object by going back to setAudioSource() step
        recorder = null
    }
}