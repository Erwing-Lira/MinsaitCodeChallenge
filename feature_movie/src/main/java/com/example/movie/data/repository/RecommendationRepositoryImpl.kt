package com.example.movie.data.repository

import com.example.movie.data.dataSource.remote.RemoteRecommendationMovieDatasource
import com.example.movie.domain.repository.RecommendedMoviesRepository
import com.example.network.data.remote.model.MovieResponse
import com.example.network.observe.NetworkObserve
import javax.inject.Inject

class RecommendationRepositoryImpl @Inject constructor(
    private val networkObserve: NetworkObserve,
    private val remoteRecommendationMovieDatasource: RemoteRecommendationMovieDatasource,
) : RecommendedMoviesRepository {
    override suspend fun getRecommendedMovies(movieId: Int): Result<MovieResponse> {
        val isInternetAvailable = networkObserve.isConnected.value

        return if (isInternetAvailable) {
            remoteRecommendationMovieDatasource.getRecommendedMovies(movieId)
        } else {
            Result.failure(Throwable("No internet connection"))
        }
    }
}