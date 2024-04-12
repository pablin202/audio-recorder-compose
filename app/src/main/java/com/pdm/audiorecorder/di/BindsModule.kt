package com.pdm.audiorecorder.di

import com.pdm.audiorecorder.data.local_database.repositories.AudioRepositoryImpl
import com.pdm.audiorecorder.domain.repositories.AudioRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {

    @Binds
    abstract fun provideAudioRepository(
        audioRepositoryImpl: AudioRepositoryImpl
    ): AudioRepository
}