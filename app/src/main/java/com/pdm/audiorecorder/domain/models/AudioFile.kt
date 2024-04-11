package com.pdm.audiorecorder.domain.models

import java.util.Date

data class AudioFile(val name: String, val duration: Long, val path: String, val creationDate: Date)
