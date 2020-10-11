package ru.l4gunner4l.cinemaonline.movielist.data.remote.model

import com.google.gson.annotations.SerializedName

data class MovieListRemoteModel(
    @SerializedName("results")
    val movies: List<MovieRemoteModel>
)