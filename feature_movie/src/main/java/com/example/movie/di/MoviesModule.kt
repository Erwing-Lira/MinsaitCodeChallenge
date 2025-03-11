package com.example.movie.di

import com.example.movie.data.dataSource.local.LocalPopularDatasource
import com.example.movie.data.dataSource.local.LocalPopularDatasourceImpl
import com.example.movie.data.dataSource.local.LocalRecommendationMovieDatasource
import com.example.movie.data.dataSource.local.LocalRecommendationMovieDatasourceImpl
import com.example.movie.data.dataSource.local.LocalTopMoviesDataSource
import com.example.movie.data.dataSource.local.LocalTopMoviesDataSourceImpl
import com.example.movie.data.dataSource.remote.RemotePopularMoviesDataSourceImpl
import com.example.movie.data.dataSource.remote.RemotePopularDatasource
import com.example.movie.data.dataSource.remote.RemoteRecommendationMovieDataSourceImpl
import com.example.movie.data.dataSource.remote.RemoteRecommendationMovieDatasource
import com.example.movie.data.dataSource.remote.RemoteTopMoviesDataSourceImpl
import com.example.movie.data.dataSource.remote.RemoteTopMoviesDatasource
import com.example.movie.data.repository.PopularMovieRepositoryImpl
import com.example.movie.data.repository.RecommendationRepositoryImpl
import com.example.movie.data.repository.TopMoviesRepositoryImpl
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
    abstract fun bindRemotePopularMovieDataSource(
        remotePopularMoviesDataSourceImpl: RemotePopularMoviesDataSourceImpl
    ): RemotePopularDatasource

    @Binds
    abstract fun bindLocalPopularMovieDataSource(
        localPopularDatasourceImpl: LocalPopularDatasourceImpl
    ): LocalPopularDatasource

    @Binds
    abstract fun bindTopMoviesRepository(
        topMoviesRepositoryImpl: TopMoviesRepositoryImpl
    ): TopMoviesRepository

    @Binds
    abstract fun bindRemoteTopMoviesDatasource(
        remoteTopMoviesDataSourceImpl: RemoteTopMoviesDataSourceImpl
    ): RemoteTopMoviesDatasource

    @Binds
    abstract fun bindLocalTopMoviesDatasource(
        localTopMoviesDataSourceImpl: LocalTopMoviesDataSourceImpl
    ): LocalTopMoviesDataSource

    @Binds
    abstract fun bindRecommendedMovieRepository(
        recommendationRepositoryImpl: RecommendationRepositoryImpl
    ): RecommendedMoviesRepository

    @Binds
    abstract fun bindRemoteRecommendedMovieDatasource(
        remoteRecommendationMovieDataSourceImpl: RemoteRecommendationMovieDataSourceImpl
    ): RemoteRecommendationMovieDatasource

    @Binds
    abstract fun bindLocaleRecommendedMovieDatasource(
        localRecommendationMovieDatasourceImpl: LocalRecommendationMovieDatasourceImpl
    ): LocalRecommendationMovieDatasource
}