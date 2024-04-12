package com.pdm.audiorecorder.domain.models

import java.util.Date

data class AudioFile(
    val id: Int = 0,
    val name: String,
    val duration: Long,
    val path: String,
    val creationDate: Date,
    val modifiedDate: Date,
    val isFavorite: Boolean = false
)
