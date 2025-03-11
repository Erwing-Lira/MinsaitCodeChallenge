package com.example.movie.data.dataSource.local

import com.example.network.data.remote.model.MovieResponse
import javax.inject.Inject

class LocalRecommendationMovieDatasourceImpl @Inject constructor(

): LocalRecommendationMovieDatasource {
    override suspend fun getRecommendedMovies(movieId: Int): Result<MovieResponse> {
        TODO("Not yet implemented")
    }
}