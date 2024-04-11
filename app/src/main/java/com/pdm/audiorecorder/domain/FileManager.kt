package com.pdm.audiorecorder.domain

import com.pdm.audiorecorder.domain.models.AudioFile
import java.io.File

interface FileManager {
    fun createNewAudioFile(): String
    fun renameAudioFile(oldName: String, newName: String): Boolean
    fun getFile(name: String): File
    fun listAudioFiles(): List<AudioFile>
}