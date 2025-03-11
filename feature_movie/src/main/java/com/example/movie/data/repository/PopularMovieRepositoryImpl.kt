package com.example.movie.data.repository

import com.example.movie.data.dataSource.local.LocalPopularDatasourceImpl
import com.example.movie.data.dataSource.remote.RemotePopularDatasource
import com.example.movie.domain.repository.PopularMoviesRepository
import com.example.network.data.remote.model.MovieResponse
import com.example.network.observe.NetworkObserve
import javax.inject.Inject

class PopularMovieRepositoryImpl @Inject constructor(
    private val networkObserve: NetworkObserve,
    private val remotePopularDatasource: RemotePopularDatasource,
    private val localPopularDatasourceImpl: LocalPopularDatasourceImpl
) : PopularMoviesRepository {
    override suspend fun getPopularMovies(): Result<MovieResponse> {
        val isInternetAvailable = networkObserve.isConnected.value

        return if (isInternetAvailable) {
            remotePopularDatasource.getPopularMovies()
        } else {
            localPopularDatasourceImpl.getPopularMovies()
        }
    }
}