package com.pdm.audiorecorder.di

import android.content.Context
import com.pdm.audiorecorder.data.AndroidAudioPlayer
import com.pdm.audiorecorder.data.AndroidRecorder
import com.pdm.audiorecorder.data.FileManagerImpl
import com.pdm.audiorecorder.domain.AudioPlayer
import com.pdm.audiorecorder.domain.AudioRecorder
import com.pdm.audiorecorder.domain.FileManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAudioRecorder(
        @ApplicationContext appContext: Context
    ): AudioRecorder {
        return AndroidRecorder(appContext)
    }

    @Singleton
    @Provides
    fun provideAudioPlayer(
        @ApplicationContext appContext: Context
    ): AudioPlayer {
        return AndroidAudioPlayer(appContext)
    }

    @Singleton
    @Provides
    fun provideFileManager(
        @ApplicationContext appContext: Context
    ): FileManager {
        return FileManagerImpl(appContext)
    }
}