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
) : AudioPlayer {

    private var player: MediaPlayer? = null

    override fun playFile(file: File) {
        player = MediaPlayer().apply {
            setDataSource(context, file.toUri())
            setOnPreparedListener {
                start()
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