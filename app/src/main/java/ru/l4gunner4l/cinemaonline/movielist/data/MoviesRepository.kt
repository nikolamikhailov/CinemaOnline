package ru.l4gunner4l.cinemaonline.movielist.data

import ru.l4gunner4l.cinemaonline.data.remote.model.MovieModel

interface MoviesRepository {

    suspend fun getMovies(): List<MovieModel>
    suspend fun getMovies(name: String): List<MovieModel>
    suspend fun getMovie(id: Long): MovieModel
}