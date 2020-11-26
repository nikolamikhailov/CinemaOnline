package ru.l4gunner4l.cinemaonline.movielist.data

import ru.l4gunner4l.cinemaonline.base.Either
import ru.l4gunner4l.cinemaonline.base.attempt
import ru.l4gunner4l.cinemaonline.data.remote.model.MovieModel

class MoviesInteractor(
    private val moviesRepository: MoviesRepository
) {
    suspend fun getMovies(): Either<Throwable, List<MovieModel>> =
        attempt { moviesRepository.getMovies() }

    suspend fun getMovies(name: String): Either<Throwable, List<MovieModel>> =
        attempt { moviesRepository.getMovies(name) }
}