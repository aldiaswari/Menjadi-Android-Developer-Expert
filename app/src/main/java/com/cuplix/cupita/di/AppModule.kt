package com.cuplix.cupita.di

import com.cuplix.cupita.core.domain.usecase.MovieInteractor
import com.cuplix.cupita.core.domain.usecase.MovieUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun provideMovieAppRepository(movieInteractor: MovieInteractor): MovieUseCase

}