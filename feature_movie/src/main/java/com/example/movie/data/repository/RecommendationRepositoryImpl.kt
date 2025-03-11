package com.example.movie.data.repository

import com.example.movie.domain.repository.RecommendedMoviesRepository
import com.example.network.data.remote.api.MovieApiService
import com.example.network.data.remote.model.MovieResponse
import javax.inject.Inject

class RecommendationRepositoryImpl @Inject constructor(
    private val movieApiService: MovieApiService
) : RecommendedMoviesRepository {
    override suspend fun getRecommendedMovies(movieId: Int): Result<MovieResponse> {
        return try {
            val response = movieApiService.getRecommendedMovies(
                movieId = movieId
            )
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Throwable("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}