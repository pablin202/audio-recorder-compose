package com.pdm.audiorecorder.data.local_database.mappers

import com.pdm.audiorecorder.data.local_database.entities.AudioFileEntity
import com.pdm.audiorecorder.domain.models.AudioFile
import java.util.Date

fun AudioFileEntity.toModel(): AudioFile {
    return AudioFile(
        id = id,
        name = name,
        duration = duration,
        path = path,
        creationDate = Date(createdAt),
        modifiedDate = Date(modifiedAt),
        isFavorite = isFavorite
    )
}

fun AudioFile.toEntity(): AudioFileEntity {
    return AudioFileEntity(
        id = id,
        name = name,
        duration = duration,
        path = path,
        createdAt = creationDate.time,
        modifiedAt = modifiedDate.time,
        isFavorite = isFavorite
    )
}