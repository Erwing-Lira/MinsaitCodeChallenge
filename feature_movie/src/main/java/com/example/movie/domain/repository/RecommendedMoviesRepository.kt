package com.example.movie.domain.repository

import com.example.network.data.remote.model.MovieResponse

interface RecommendedMoviesRepository {
    suspend fun getRecommendedMovies(movieId: Int): Result<MovieResponse>
}