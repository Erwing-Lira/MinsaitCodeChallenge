package com.example.movie.domain.useCase

import com.example.movie.domain.repository.TopMoviesRepository
import com.example.network.data.remote.model.MovieResponse
import javax.inject.Inject

class FetchTopMovies @Inject constructor(
    private val topMoviesRepository: TopMoviesRepository
) {
    suspend operator fun invoke(): Result<MovieResponse> {
        return topMoviesRepository.getTopMovies()
    }
}