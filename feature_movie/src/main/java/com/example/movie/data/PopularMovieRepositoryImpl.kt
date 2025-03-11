package com.example.movie.data

import com.example.movie.domain.repository.PopularMoviesRepository
import com.example.network.data.remote.api.MovieApiService
import com.example.network.data.remote.model.MovieResponse
import javax.inject.Inject

class PopularMovieRepositoryImpl @Inject constructor(
    private val movieApiService: MovieApiService
) : PopularMoviesRepository {
    override suspend fun getPopularMovies(): Result<MovieResponse> {
        return try {
            val response = movieApiService.getPopularMovies()
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