package com.pdm.audiorecorder.data.local_database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pdm.audiorecorder.util.Constants.DATABASE_TABLE
import java.util.Date

@Entity(tableName = DATABASE_TABLE)
data class AudioFileEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val duration: Long,
    val path: String,
    @ColumnInfo(name = "created_at") var createdAt: Long,
    @ColumnInfo(name = "modified_at") var modifiedAt: Long,
    val isFavorite: Boolean = false
)
