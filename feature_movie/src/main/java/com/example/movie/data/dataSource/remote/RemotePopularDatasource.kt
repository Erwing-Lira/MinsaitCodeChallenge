package com.example.movie.data.dataSource.remote

import com.example.network.data.remote.model.MovieResponse

interface RemotePopularDatasource {
    suspend fun getPopularMovies(): Result<MovieResponse>
}