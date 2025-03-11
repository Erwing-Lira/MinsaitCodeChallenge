package com.example.movie.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie.domain.useCase.FetchPopularMovies
import com.example.movie.domain.useCase.FetchRecommendedMovies
import com.example.movie.domain.useCase.FetchTopMovies
import com.example.movie.ui.MovieState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val fetchPopularMovies: FetchPopularMovies,
    private val fetchTopMovies: FetchTopMovies,
    private val fetchRecommendedMovies: FetchRecommendedMovies
): ViewModel() {
    private val _popularState = MutableStateFlow<MovieState>(MovieState.Loading)
    val popularState: StateFlow<MovieState> = _popularState

    private val _bestState = MutableStateFlow<MovieState>(MovieState.Loading)
    val bestState: StateFlow<MovieState> = _bestState

    private val _recommendedState = MutableStateFlow<MovieState>(MovieState.Loading)
    val recommendedState: StateFlow<MovieState> = _recommendedState

    init {
        fetchPopularData()
        fetchTopData()
    }

    private fun fetchPopularData() {
        viewModelScope.launch {
            fetchPopularMovies.invoke()
                .onSuccess { data ->
                    _popularState.update {
                        MovieState.Success(data.results)
                    }
                }
                .onFailure { error ->
                    _popularState.update {
                        MovieState.Error(error.message ?: "Unknown error")
                    }
                }
        }
    }

    private fun fetchTopData() {
        viewModelScope.launch {
            fetchTopMovies.invoke()
                .onSuccess { data ->
                    _bestState.update {
                        MovieState.Success(data.results)
                    }
                }
                .onFailure { error ->
                    _bestState.update {
                        MovieState.Error(error.message ?: "Unknown error")
                    }
                }
        }
    }

    fun getRecommendedMovies(id: Int) {
        viewModelScope.launch {
            _recommendedState.update {
                MovieState.Loading
            }
            fetchRecommendedMovies.invoke(id)
                .onSuccess { data ->
                    _recommendedState.update {
                        MovieState.Success(data.results)
                    }
                }
                .onFailure { error ->
                    _recommendedState.update {
                        MovieState.Error(error.message ?: "Unknown error")
                    }
                }
        }
    }
}