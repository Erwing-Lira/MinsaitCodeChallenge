package com.example.movie.data.dataSource.remote

import com.example.network.data.remote.model.MovieResponse

interface RemoteTopMoviesDatasource {
    suspend fun getTopMovies(): Result<MovieResponse>
}