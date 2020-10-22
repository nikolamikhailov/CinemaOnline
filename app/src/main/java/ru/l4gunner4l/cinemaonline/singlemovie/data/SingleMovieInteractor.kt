package ru.l4gunner4l.cinemaonline.singlemovie.data

import ru.l4gunner4l.cinemaonline.movielist.data.MoviesRepository

class SingleMovieInteractor(
    private val moviesRepository: MoviesRepository
) {
    suspend fun getMovie(id: Long) = moviesRepository.getMovie(id)
}