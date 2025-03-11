package com.example.movie.data.repository

import com.example.movie.domain.repository.TopMoviesRepository
import com.example.network.data.remote.api.MovieApiService
import com.example.network.data.remote.model.MovieResponse
import javax.inject.Inject

class TopMoviesRepositoryImpl @Inject constructor(
    private val movieApiService: MovieApiService
) : TopMoviesRepository {
    override suspend fun getTopMovies(): Result<MovieResponse> {
        return try {
            val response = movieApiService.getTopRatedMovies()
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