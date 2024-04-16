package com.pdm.audiorecorder.domain.models

import java.util.Date

data class AudioFile(
    val id: Int = 0,
    val name: String = "",
    val duration: Long = 0,
    val path: String = "",
    val creationDate: Date = Date(),
    val modifiedDate: Date = Date(),
    val isFavorite: Boolean = false
)
