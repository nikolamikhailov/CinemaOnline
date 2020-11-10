package ru.l4gunner4l.cinemaonline.movielist.data

import ru.l4gunner4l.cinemaonline.data.remote.model.MovieModel
import ru.l4gunner4l.cinemaonline.data.remote.model.MoviesRemoteSource

class MoviesRepositoryImpl(private val remote: MoviesRemoteSource) : MoviesRepository {

    override suspend fun getMovies(): List<MovieModel> {
        return remote.getMoviesResponse().movies
    }

    override suspend fun getMovies(name: String): List<MovieModel> {
        return remote.getMoviesResponse().movies.filter { it.title.toLowerCase() == name }
    }

    override suspend fun getMovie(id: Long): MovieModel {
        return remote.getMoviesResponse().movies.find { it.id == id }!!
    }

}