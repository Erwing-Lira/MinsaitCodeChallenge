package com.example.movie.data.dataSource.local

import com.example.network.data.remote.model.MovieResponse
import javax.inject.Inject

class LocalTopMoviesDataSourceImpl @Inject constructor(

): LocalTopMoviesDataSource {
    override suspend fun getTopMovies(): Result<MovieResponse> {
        TODO("Not yet implemented")
    }
}