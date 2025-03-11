package com.example.movie.data.dataSource.remote

import com.example.network.data.remote.model.MovieResponse

interface RemoteRecommendationMovieDatasource {
    suspend fun getRecommendedMovies(movieId: Int): Result<MovieResponse>
}