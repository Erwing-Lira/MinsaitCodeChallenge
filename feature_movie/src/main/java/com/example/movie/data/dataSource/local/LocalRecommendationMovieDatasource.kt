package com.example.movie.data.dataSource.local

import com.example.network.data.remote.model.MovieResponse

interface LocalRecommendationMovieDatasource {
    suspend fun getRecommendedMovies(movieId: Int): Result<MovieResponse>
}