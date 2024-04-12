package com.pdm.audiorecorder.data.local_database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pdm.audiorecorder.data.local_database.entities.AudioFileEntity

@Database(entities = [AudioFileEntity::class], version = 1, exportSchema = false)
abstract class AudioDatabase : RoomDatabase() {
    abstract fun audioDao(): AudioDao
}