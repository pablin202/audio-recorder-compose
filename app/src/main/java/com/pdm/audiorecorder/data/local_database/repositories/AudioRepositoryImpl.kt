package com.pdm.audiorecorder.data.local_database.repositories

import com.pdm.audiorecorder.data.local_database.AudioDao
import com.pdm.audiorecorder.data.local_database.mappers.toEntity
import com.pdm.audiorecorder.data.local_database.mappers.toModel
import com.pdm.audiorecorder.domain.models.AudioFile
import com.pdm.audiorecorder.domain.repositories.AudioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AudioRepositoryImpl @Inject constructor(
    private val audioDao: AudioDao
) : AudioRepository {
    override val getAllAudios: Flow<List<AudioFile>>
        get() = audioDao.getAllAudios().map { entity -> entity.map { it.toModel() } }

    override fun getAudioById(audioId: Int): Flow<AudioFile> = flow {

        val model = withContext(Dispatchers.IO) {
            audioDao.getAudioById(audioId).toModel()
        }

        emit(model)
    }

    override suspend fun addAudio(audioFile: AudioFile) {
        audioDao.addAudio(audioFile.toEntity())
    }

    override suspend fun updateAudio(audioFile: AudioFile) {
        audioDao.updateAudio(audioFile.toEntity())
    }

    override suspend fun deleteAudio(audioFile: AudioFile) {
        audioDao.deleteAudio(audioFile.id)
    }

}