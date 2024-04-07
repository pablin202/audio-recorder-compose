package com.pdm.audiorecorder.domain

interface FileManager {
    fun createNewAudioFile(): String
    fun listAudioFiles(): List<String>
    fun renameAudioFile(oldName: String, newName: String): Boolean
}