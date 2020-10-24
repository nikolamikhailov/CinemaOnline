package ru.l4gunner4l.cinemaonline.data.remote

import retrofit2.http.GET
import ru.l4gunner4l.cinemaonline.data.remote.model.MoviesResponse

interface MoviesApi {

    @GET("movies.json")
    suspend fun getMoviesResponse(): MoviesResponse
}