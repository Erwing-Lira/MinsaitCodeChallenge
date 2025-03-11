package com.example.movie.data.dataSource.local

import com.example.network.data.remote.model.MovieResponse

interface LocalPopularDatasource {
    suspend fun getPopularMovies(): Result<MovieResponse>
}