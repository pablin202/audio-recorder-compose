package com.pdm.audiorecorder.data

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Build
import com.pdm.audiorecorder.domain.AudioRecorder
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import android.media.audiofx.Visualizer
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class AndroidRecorder @Inject constructor(private val context: Context) : AudioRecorder {
//
//    private var audioRecord: AudioRecord? = null
//    private var visualizer: Visualizer? = null
//    private var recordingFilePath: String? = null
//
//    @SuppressLint("MissingPermission")
//    override fun start(outputFile: File): Visualizer? {
//        recordingFilePath = outputFile.absolutePath
//
//        val sampleRate = 44100 // Hz
//        val channelConfig = android.media.AudioFormat.CHANNEL_IN_MONO
//        val audioFormat = android.media.AudioFormat.ENCODING_PCM_16BIT
//        val minBufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)
//
//        audioRecord = AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate, channelConfig, audioFormat, minBufferSize)
//
//        audioRecord?.startRecording()
//
////        audioRecord?.audioSessionId?.let { sessionId ->
////            visualizer = Visualizer(sessionId).apply {
////                captureSize = Visualizer.getCaptureSizeRange()[1]
////                setDataCaptureListener(object : Visualizer.OnDataCaptureListener {
////                    override fun onWaveFormDataCapture(visualizer: Visualizer?, waveform: ByteArray?, samplingRate: Int) {
////                        // Aquí se manejarían los datos de la forma de onda para la visualización.
////                    }
////
////                    override fun onFftDataCapture(visualizer: Visualizer?, fft: ByteArray?, samplingRate: Int) {
////                        // FFT data no es necesario en este caso.
////                    }
////                }, Visualizer.getMaxCaptureRate() / 2, true, false)
////                enabled = true
////            }
////        }
//
//        return visualizer
//    }
//
//    override fun stop() {
//        audioRecord?.stop()
//        audioRecord?.release()
//        audioRecord = null
//
//        visualizer?.enabled = false
//        visualizer?.release()
//        visualizer = null
//    }

    private var recorder: MediaRecorder? = null

    private fun createRecorder(): MediaRecorder {
        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else MediaRecorder()
    }

    override fun start(outputFile: File) {
        createRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(FileOutputStream(outputFile).fd)

            prepare()
            start()

            recorder = this
        }
    }

    override fun stop() {
        recorder?.stop()
        recorder?.reset()
        recorder = null
    }
}