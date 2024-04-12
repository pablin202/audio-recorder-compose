package com.pdm.audiorecorder.data.local_database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pdm.audiorecorder.data.local_database.entities.AudioFileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AudioDao {

    @Query("SELECT * FROM audio_table ORDER BY id DESC")
    fun getAllAudios(): Flow<List<AudioFileEntity>>

    @Query("SELECT * FROM audio_table WHERE id=:audioId")
    fun getAudioById(audioId: Int): AudioFileEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAudio(audioFileEntity: AudioFileEntity)

    @Update
    suspend fun updateAudio(audioFileEntity: AudioFileEntity)

    @Query("DELETE FROM audio_table WHERE id=:audioId")
    suspend fun deleteAudio(audioId: Int)
}