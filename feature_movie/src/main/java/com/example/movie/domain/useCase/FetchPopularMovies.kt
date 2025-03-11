package com.example.movie.domain.useCase

import com.example.movie.domain.repository.PopularMoviesRepository
import com.example.network.data.remote.model.MovieResponse
import javax.inject.Inject

class FetchPopularMovies @Inject constructor(
    private val popularMoviesRepository: PopularMoviesRepository
) {
    suspend operator fun invoke(): Result<MovieResponse> {
        return popularMoviesRepository.getPopularMovies()
    }
}