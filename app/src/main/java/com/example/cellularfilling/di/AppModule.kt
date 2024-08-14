package com.example.cellularfilling.di

import com.example.cellularfilling.data.CellRepositoryImpl
import com.example.cellularfilling.domain.repository.CellRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Binds
    @Singleton
    fun bindCellRepository(repository: CellRepositoryImpl): CellRepository
}