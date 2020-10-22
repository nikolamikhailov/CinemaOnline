package ru.l4gunner4l.cinemaonline.movielist.data

import ru.l4gunner4l.cinemaonline.movielist.ui.model.MovieListItem
import ru.l4gunner4l.cinemaonline.singlemovie.ui.model.SingleMovieItem

interface MoviesRepository {

    suspend fun getMovies(): List<MovieListItem>
    suspend fun getMovie(id: Long): SingleMovieItem
}