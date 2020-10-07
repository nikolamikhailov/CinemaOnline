package ru.l4gunner4l.cinemaonline.movielist.data.remote.model

import ru.l4gunner4l.cinemaonline.movielist.data.remote.MoviesApi

class MoviesRemoteSource(private val moviesApi: MoviesApi) {
    suspend fun getMoviesResponse() = moviesApi.getMoviesResponse()
}