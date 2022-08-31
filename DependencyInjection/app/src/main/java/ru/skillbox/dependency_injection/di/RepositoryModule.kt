package ru.skillbox.dependency_injection.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.skillbox.dependency_injection.data.ImageRepository
import ru.skillbox.dependency_injection.data.ImagesRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule() {
@Binds
abstract fun providesImageRepository(impl: ImagesRepositoryImpl):ImageRepository

@Binds
abstract fun provideContext( @ApplicationContext context: Context):Context

}