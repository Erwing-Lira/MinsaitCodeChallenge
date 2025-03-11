package com.example.movie.data.dataSource.local

import com.example.network.data.remote.model.MovieResponse
import javax.inject.Inject

class LocalPopularDatasourceImpl @Inject constructor(

): LocalPopularDatasource {
    override suspend fun getPopularMovies(): Result<MovieResponse> {
        TODO("Not yet implemented")
    }
}