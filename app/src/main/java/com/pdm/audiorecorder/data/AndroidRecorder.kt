package com.pdm.audiorecorder.data

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import com.pdm.audiorecorder.domain.AudioRecorder
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class AndroidRecorder @Inject constructor(private val context: Context) : AudioRecorder {

    private var recorder: MediaRecorder? = null
    private var currentFilePath: File? = null

    private fun createRecorder(): MediaRecorder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else MediaRecorder()
    }

    override fun start(outputFile: File): Flow<Int> {
        createRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(FileOutputStream(outputFile).fd)

            prepare()
            start()
            currentFilePath = outputFile
            recorder = this
        }

        return callbackFlow {
            val amplitudeChecker = object : Runnable {
                override fun run() {
                    recorder?.let {
                        try {
                            val maxAmplitude = it.maxAmplitude
                            trySend(maxAmplitude)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    handler.postDelayed(this, 100)
                }
            }

            handler.post(amplitudeChecker)

            awaitClose {
                handler.removeCallbacks(amplitudeChecker)
            }
        }
    }

    override fun stop(onNewRecord: (file: File) -> Unit) {
        recorder?.stop()
        recorder?.reset()
        recorder = null
        currentFilePath?.let {
            onNewRecord(it)
        }
        currentFilePath = null
    }

    companion object {
        private val handler = android.os.Handler(android.os.Looper.getMainLooper())
    }
}