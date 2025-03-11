package com.example.movie.domain.repository

import com.example.network.data.remote.model.MovieResponse

interface PopularMoviesRepository {
    suspend fun getPopularMovies(): Result<MovieResponse>
}