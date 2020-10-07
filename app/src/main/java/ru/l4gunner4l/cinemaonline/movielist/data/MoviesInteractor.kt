package ru.l4gunner4l.cinemaonline.movielist.data

class MoviesInteractor(
    private val moviesRepository: MoviesRepository
) {
    suspend fun getMovies() = moviesRepository.getMovies()
}