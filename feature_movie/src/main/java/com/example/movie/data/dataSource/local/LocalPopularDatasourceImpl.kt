package com.example.movie.data.dataSource.local

import com.example.database.dao.PopularMovieDao
import com.example.database.entity.PopularMovie
import com.example.network.data.remote.model.MovieDTO
import com.example.network.data.remote.model.MovieResponse
import javax.inject.Inject

class LocalPopularDatasourceImpl @Inject constructor(
    private val popularMovieDao: PopularMovieDao
): LocalPopularDatasource {
    override suspend fun getPopularMovies(): Result<MovieResponse> {
        return Result.success(
            MovieResponse(
                page = 1,
                total_pages = 1,
                total_results = 1,
                results = popularMovieDao.getAllPopularMovies().map {
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
            PopularMovie(
                id = it.id,
                original_title = it.original_title,
                poster_path = it.poster_path,
                release_date = it.release_date
            )
        }
        popularMovieDao.insertPopularMovies(movies)
    }

    override suspend fun deleteAll() {
        popularMovieDao.deleteAll()
    }
}