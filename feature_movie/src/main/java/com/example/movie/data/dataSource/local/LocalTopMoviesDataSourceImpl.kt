package com.example.movie.data.dataSource.local

import com.example.database.dao.TopMovieDao
import com.example.database.entity.TopMovies
import com.example.network.data.remote.model.MovieDTO
import com.example.network.data.remote.model.MovieResponse
import javax.inject.Inject

class LocalTopMoviesDataSourceImpl @Inject constructor(
    private val topMovieDao: TopMovieDao
): LocalTopMoviesDataSource {
    override suspend fun getTopMovies(): Result<MovieResponse> {
        return Result.success(
            MovieResponse(
                page = 1,
                total_pages = 1,
                total_results = 1,
                results = topMovieDao.getAllTopMovies().map {
                    MovieDTO(
                        it.id,
                        it.original_title,
                        it.poster_path,
                        it.release_date
                    )
                }
            )
        )
    }

    override suspend fun saveData(listPopular: List<MovieDTO>) {
        val movies = listPopular.map {
            TopMovies(
                id = it.id,
                original_title = it.original_title,
                poster_path = it.poster_path,
                release_date = it.release_date
            )
        }
        topMovieDao.insertTopMovies(movies)
    }

    override suspend fun deleteAll() {
        topMovieDao.deleteAll()
    }
}