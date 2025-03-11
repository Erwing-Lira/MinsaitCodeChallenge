package com.example.movie.data.repository

import com.example.movie.data.dataSource.local.LocalPopularDatasource
import com.example.movie.data.dataSource.remote.RemotePopularDatasource
import com.example.movie.domain.repository.PopularMoviesRepository
import com.example.network.data.remote.model.MovieResponse
import com.example.network.observe.NetworkObserve
import javax.inject.Inject

class PopularMovieRepositoryImpl @Inject constructor(
    private val networkObserve: NetworkObserve,
    private val remotePopularDatasource: RemotePopularDatasource,
    private val localPopularDatasource: LocalPopularDatasource
) : PopularMoviesRepository {
    override suspend fun getPopularMovies(): Result<MovieResponse> {
        val isInternetAvailable = networkObserve.isConnected.value

        return if (isInternetAvailable) {
            val response = remotePopularDatasource.getPopularMovies()
            response
                .onSuccess { data ->
                    localPopularDatasource.deleteAll()
                    localPopularDatasource.saveData(data.results)
                    return response
                }
                .onFailure {
                    return localPopularDatasource.getPopularMovies()
                }
        } else {
            localPopularDatasource.getPopularMovies()
        }
    }
}