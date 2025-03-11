package com.example.movie.data.repository

import com.example.movie.data.dataSource.remote.RemoteTopMoviesDatasource
import com.example.movie.domain.repository.TopMoviesRepository
import com.example.network.data.remote.model.MovieResponse
import com.example.network.observe.NetworkObserve
import javax.inject.Inject

class TopMoviesRepositoryImpl @Inject constructor(
    private val networkObserve: NetworkObserve,
    private val remoteTopMoviesDatasource: RemoteTopMoviesDatasource,
    private val localTopMoviesDatasource: RemoteTopMoviesDatasource
) : TopMoviesRepository {
    override suspend fun getTopMovies(): Result<MovieResponse> {
        val isconnected = networkObserve.isConnected.value

        return if (isconnected) {
            remoteTopMoviesDatasource.getTopMovies()
        } else {
            localTopMoviesDatasource.getTopMovies()
        }
    }
}