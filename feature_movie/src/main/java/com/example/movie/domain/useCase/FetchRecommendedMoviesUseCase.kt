package com.example.movie.domain.useCase

import com.example.movie.domain.repository.RecommendedMoviesRepository
import com.example.network.data.remote.model.MovieResponse
import javax.inject.Inject

class FetchRecommendedMoviesUseCase @Inject constructor(
    private val recommendedMoviesRepository: RecommendedMoviesRepository
) {
    suspend operator fun invoke(movieId: Int): Result<MovieResponse> {
        return recommendedMoviesRepository.getRecommendedMovies(movieId)
    }
}