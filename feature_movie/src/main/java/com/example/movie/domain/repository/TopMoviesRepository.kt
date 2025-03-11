package com.example.movie.domain.repository

import com.example.network.data.remote.model.MovieResponse

interface TopMoviesRepository {
    suspend fun getTopMovies(): Result<MovieResponse>
}