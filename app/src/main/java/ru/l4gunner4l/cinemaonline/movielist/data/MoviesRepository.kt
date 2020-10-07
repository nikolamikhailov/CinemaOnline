package ru.l4gunner4l.cinemaonline.movielist.data

import ru.l4gunner4l.cinemaonline.movielist.ui.MovieListItem

interface MoviesRepository {

    suspend fun getMovies(): List<MovieListItem>
}