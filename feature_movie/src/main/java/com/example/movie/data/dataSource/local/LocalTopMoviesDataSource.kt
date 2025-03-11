package com.example.movie.data.dataSource.local

import com.example.network.data.remote.model.MovieResponse

interface LocalTopMoviesDataSource {
    suspend fun getTopMovies(): Result<MovieResponse>
}