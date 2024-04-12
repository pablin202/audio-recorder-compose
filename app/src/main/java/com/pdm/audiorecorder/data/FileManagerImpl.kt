package com.pdm.audiorecorder.data

import android.content.Context
import android.media.MediaMetadataRetriever
import android.provider.MediaStore
import android.util.Log
import com.pdm.audiorecorder.domain.models.AudioFile
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

    override fun listAudioFiles(): List<AudioFile> {

        val audioList = mutableListOf<AudioFile>()
        val filesDir = context.filesDir

        val mp3Files = filesDir.listFiles { _, name -> name.endsWith(".mp3", ignoreCase = true) }
        mp3Files?.forEach { file ->
            val name = file.name
            val duration = getDurationOfFile(file.path)
            val creationDate = Date(file.lastModified())
            if (duration > 0L) {
                val audioFile =
                    AudioFile(0, name = name, duration, file.path, creationDate, creationDate)
                audioList.add(audioFile)
            }
        }
        return audioList
    }

    override fun getAudioFile(file: File): AudioFile? {
        val name = file.name
        val duration = getDurationOfFile(file.path)
        val creationDate = Date(file.lastModified())
        return if (duration > 0L) {
            AudioFile(0, name = name, duration, file.path, creationDate, creationDate)
        } else {
            null
        }
    }

    override fun renameAudioFile(oldName: String, newName: String): Boolean {
        val oldFile = File(context.filesDir, oldName)
        val newFile = File(context.filesDir, newName)
        return oldFile.renameTo(newFile)
    }

    override fun getFile(name: String): File {
        return File(context.filesDir.absolutePath + File.separator + name)
    }

    private fun getDurationOfFile(filePath: String): Long {
        return try {
            val retriever = MediaMetadataRetriever()
            retriever.setDataSource(filePath)
            val durationStr =
                retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
            val durationMs = durationStr?.toLong() ?: 0L
            retriever.release()
            durationMs
        } catch (e: Exception) {
            Log.e("getDurationOfFile", e.message.toString())
            0L
        }
    }
}