package com.pdm.audiorecorder.domain.repositories

import com.pdm.audiorecorder.domain.models.AudioFile
import kotlinx.coroutines.flow.Flow

interface AudioRepository {
    val getAllAudios: Flow<List<AudioFile>>

    fun getAudioById(audioId: Int): Flow<AudioFile>

    suspend fun addAudio(audioFile: AudioFile)

    suspend fun updateAudio(audioFile: AudioFile)

    suspend fun deleteAudio(audioFile: AudioFile)
}