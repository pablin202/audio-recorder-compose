package com.pdm.audiorecorder.domain

import java.io.File

interface FileManager {
    fun createNewAudioFile(): String
    fun listAudioFiles(): List<String>
    fun renameAudioFile(oldName: String, newName: String): Boolean
    fun getFile(name: String): File
}