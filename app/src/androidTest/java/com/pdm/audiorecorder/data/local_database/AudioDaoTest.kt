package com.pdm.audiorecorder.data.local_database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pdm.audiorecorder.data.local_database.entities.AudioFileEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AudioDaoTest {
    private lateinit var db: AudioDatabase
    private lateinit var audioDao: AudioDao

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AudioDatabase::class.java
        ).allowMainThreadQueries().build()
        audioDao = db.audioDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndGetAudio() = runTest {
        val audio = AudioFileEntity(1, "Test Audio", 3000, "test_path.mp3", 1L, 123L, false)
        audioDao.addAudio(audio)

        val byId = audioDao.getAudioById(1)
        assertEquals(audio, byId)
    }

    @Test
    fun updateAndGetAudio() = runTest {
        val audio = AudioFileEntity(1, "Test Audio", 3000, "test_path.mp3", 1L, 123L, false)
        audioDao.addAudio(audio)

        val updatedAudio =
            AudioFileEntity(1, "Update Test Audio", 3000, "test_path.mp3", 1L, 123L, false)
        audioDao.updateAudio(updatedAudio)

        val byId = audioDao.getAudioById(1)
        assertEquals(updatedAudio, byId)
    }

    @Test
    fun deleteAudioAndGetAll() = runTest {
        val audio1 = AudioFileEntity(1, "Test Audio 1", 3000, "test_path.mp3", 1L, 123L, false)
        val audio2 = AudioFileEntity(2, "Test Audio 2", 3000, "test_path.mp3", 1L, 123L, false)
        audioDao.addAudio(audio1)
        audioDao.addAudio(audio2)

        audioDao.deleteAudio(1)
        val allAudios = audioDao.getAllAudios().first()
        assertEquals(1, allAudios.size)
        assertEquals(audio2, allAudios[0])
    }
}