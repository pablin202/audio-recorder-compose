package com.pdm.audiorecorder.data

import android.content.Context
import android.media.MediaPlayer
import androidx.test.core.app.ApplicationProvider
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.io.File

@RunWith(MockitoJUnitRunner::class)
class AndroidAudioPlayerTest {
    @Mock
    private lateinit var mockContext: Context

    @Mock
    private lateinit var mockMediaPlayer: MediaPlayer

    private lateinit var androidAudioPlayer: AndroidAudioPlayer

    @Before
    fun setUp() {
        val realContext = ApplicationProvider.getApplicationContext<Context>()
        // Initialize your class with mocked dependencies
        androidAudioPlayer = AndroidAudioPlayer(mockContext)
        // Assume MediaPlayer.create() returns a mock MediaPlayer instance
        `when`(MediaPlayer.create(realContext, anyInt())).thenReturn(
            mockMediaPlayer
        )
    }


    @Test
    fun playFile_with_File_starts_MediaPlayer() {
        val dummyFile = File("path/to/audio.mp3")
        androidAudioPlayer.playFile(dummyFile) {

        }

        // Verify MediaPlayer was prepared and started
        verify(mockMediaPlayer).prepare()
        verify(mockMediaPlayer).start()
    }

    @Test
    fun stop_stops_and_releases_MediaPlayer() {
        androidAudioPlayer.stop()

        // Verify MediaPlayer was stopped and released
        verify(mockMediaPlayer).stop()
        verify(mockMediaPlayer).release()
    }

    @Test
    fun pause_pauses_MediaPlayer() {
        androidAudioPlayer.pause()
        // Verify MediaPlayer was paused
        verify(mockMediaPlayer).pause()
    }

    @Test
    fun getAudioSessionId_returns_correct_session_ID() {
        `when`(mockMediaPlayer.audioSessionId).thenReturn(123)

        val sessionId = androidAudioPlayer.getAudioSessionId()

        verify(mockMediaPlayer).audioSessionId
        assertEquals(123, sessionId)
    }
}