package com.pdm.audiorecorder.data

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import androidx.core.net.toUri
import com.pdm.audiorecorder.domain.AudioPlayer
import java.io.File
import javax.inject.Inject

class AndroidAudioPlayer @Inject constructor(
    private val context: Context
) : AudioPlayer {

    private var player: MediaPlayer? = null

    override fun playFile(file: File, onCompletion: () -> Unit) {
        playAudio(file.toUri()) {
            onCompletion()
        }
    }

    override fun playFile(file: String, onCompletion: () -> Unit) {
        playAudio(file.toUri()) {
            onCompletion()
        }
    }

    private fun playAudio(uri: Uri, onCompletion: () -> Unit) {
        player = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(context, uri)
            prepare()
            start()
            setOnCompletionListener {
                onCompletion()
            }
        }
    }

    override fun stop() {
        player?.stop()
        player?.release()
        player = null
    }

    override fun pause() {
        player?.pause()
    }

    override fun getAudioSessionId(): Int? = player?.audioSessionId
}