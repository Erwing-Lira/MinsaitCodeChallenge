package com.example.movie.di

import com.example.movie.data.PopularMovieRepositoryImpl
import com.example.movie.data.RecommendationRepositoryImpl
import com.example.movie.data.TopMoviesRepositoryImpl
import com.example.movie.domain.repository.PopularMoviesRepository
import com.example.movie.domain.repository.RecommendedMoviesRepository
import com.example.movie.domain.repository.TopMoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MoviesModule {
    @Binds
    abstract fun bindPopularMovieRepository(
        popularMovieRepositoryImpl: PopularMovieRepositoryImpl
    ): PopularMoviesRepository

    @Binds
    abstract fun bindTopMoviesRepository(
        topMoviesRepositoryImpl: TopMoviesRepositoryImpl
    ): TopMoviesRepository

    @Binds
    abstract fun bindRecommendedMovieRepository(
        recommendationRepositoryImpl: RecommendationRepositoryImpl
    ): RecommendedMoviesRepository
}