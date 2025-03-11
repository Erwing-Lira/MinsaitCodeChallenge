package com.example.movie.ui.state

import com.example.network.data.remote.model.MovieDTO

sealed interface MovieState {
    data object Loading : MovieState
    data class Success(val movies: List<MovieDTO>) : MovieState
    data class Error(val message: String) : MovieState
}