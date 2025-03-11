package com.example.movie.data.repository

import com.example.movie.data.dataSource.local.LocalTopMoviesDataSource
import com.example.movie.data.dataSource.remote.RemoteTopMoviesDatasource
import com.example.movie.domain.repository.TopMoviesRepository
import com.example.network.data.remote.model.MovieResponse
import com.example.network.observe.NetworkObserve
import javax.inject.Inject

class TopMoviesRepositoryImpl @Inject constructor(
    private val networkObserve: NetworkObserve,
    private val remoteTopMoviesDatasource: RemoteTopMoviesDatasource,
    private val localTopMoviesDatasource: LocalTopMoviesDataSource
) : TopMoviesRepository {
    override suspend fun getTopMovies(): Result<MovieResponse> {
        val isInternetAvailable = networkObserve.isConnected.value

        return if (isInternetAvailable) {
            val response = remoteTopMoviesDatasource.getTopMovies()
            response
                .onSuccess { data ->
                    localTopMoviesDatasource.deleteAll()
                    localTopMoviesDatasource.saveData(data.results)
                    return response
                }
                .onFailure {
                    return localTopMoviesDatasource.getTopMovies()
                }
        } else {
            localTopMoviesDatasource.getTopMovies()
        }
    }
}