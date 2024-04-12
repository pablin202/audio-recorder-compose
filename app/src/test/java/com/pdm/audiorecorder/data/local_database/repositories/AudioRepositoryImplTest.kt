package com.pdm.audiorecorder.data.local_database.repositories

import com.pdm.audiorecorder.data.local_database.AudioDao
import com.pdm.audiorecorder.data.local_database.entities.AudioFileEntity
import com.pdm.audiorecorder.data.local_database.mappers.toEntity
import com.pdm.audiorecorder.domain.models.AudioFile
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import java.util.Date

@ExperimentalCoroutinesApi
class AudioRepositoryImplTest {
    @Mock
    private lateinit var audioDao: AudioDao

    private lateinit var audioRepositoryImpl: AudioRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        audioRepositoryImpl = AudioRepositoryImpl(audioDao)
    }

    @Test
    fun `getAllAudios returns correct data`() = runTest {
        val audioList = listOf(
            AudioFileEntity(
                1,
                "Test Audio",
                3000,
                "test_path.mp3",
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                true
            )
        )
        whenever(audioDao.getAllAudios()).thenReturn(flowOf(audioList))

        val result = audioRepositoryImpl.getAllAudios.first()

        assert(result.size == 1)
        assert(result[0].id == audioList[0].id)
        assert(result[0].name == audioList[0].name)
        assert(result[0].isFavorite == audioList[0].isFavorite)
    }

    @Test
    fun `getAudioById returns correct audio`() = runTest {
        val audioId = 1
        val audioEntity = AudioFileEntity(
            1,
            "Test Audio",
            3000,
            "test_path.mp3",
            System.currentTimeMillis(),
            System.currentTimeMillis(),
            true
        )
        whenever(audioDao.getAudioById(audioId)).thenReturn(audioEntity)

        val result = audioRepositoryImpl.getAudioById(audioId).first()

        assert(result.id == audioEntity.id)
        assert(result.name == audioEntity.name)
        assert(result.isFavorite == audioEntity.isFavorite)
    }

    @Test
    fun `addAudio calls dao with correct parameters`() = runTest {
        val audioFile = AudioFile(
            1, "Test Audio", 3000, "test_path.mp3",
            Date(), Date(), true
        )
        audioRepositoryImpl.addAudio(audioFile)

        verify(audioDao).addAudio(audioFile.toEntity())
    }

    @Test
    fun `updateAudio calls dao with correct parameters`() = runTest {
        val audioFile = AudioFile(
            1, "Test Audio", 300, "test_path.mp3",
            Date(), Date(), true
        )
        audioRepositoryImpl.updateAudio(audioFile)

        verify(audioDao).updateAudio(audioFile.toEntity())
    }

    @Test
    fun `deleteAudio calls dao with correct parameters`() = runTest {
        val audioFile = AudioFile(
            1, "Test Audio", 300, "test_path.mp3",
            Date(), Date(), true
        )
        audioRepositoryImpl.deleteAudio(audioFile)

        verify(audioDao).deleteAudio(audioFile.id)
    }
}