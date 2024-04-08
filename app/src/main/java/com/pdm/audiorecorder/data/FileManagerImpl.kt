package com.pdm.audiorecorder.data

import android.content.Context
import com.pdm.audiorecorder.domain.FileManager
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class FileManagerImpl @Inject constructor(private val context: Context) : FileManager {
    override fun createNewAudioFile(): String {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileName = "recording_$timeStamp.mp3"
        return context.filesDir.absolutePath + File.separator + fileName
    }

    override fun listAudioFiles(): List<String> {
        val fileList = mutableListOf<String>()
        val filesDir = context.filesDir
        val files = filesDir.listFiles { _, name -> name.endsWith(".mp3") }
        files?.forEach {
            fileList.add(it.name)
        }
        return fileList.sorted()
    }


    override fun renameAudioFile(oldName: String, newName: String): Boolean {
        val oldFile = File(context.filesDir, oldName)
        val newFile = File(context.filesDir, newName)
        return oldFile.renameTo(newFile)
    }

    override fun getFile(name: String): File {
        return File(context.filesDir.absolutePath + File.separator + name)
    }
}