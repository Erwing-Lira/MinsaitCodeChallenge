package com.example.movie.data.dataSource.remote

import com.example.network.data.remote.api.MovieApiService
import com.example.network.data.remote.model.MovieResponse
import javax.inject.Inject

class RemoteTopMoviesDataSourceImpl @Inject constructor(
    private val movieApiService: MovieApiService
): RemoteTopMoviesDatasource {
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