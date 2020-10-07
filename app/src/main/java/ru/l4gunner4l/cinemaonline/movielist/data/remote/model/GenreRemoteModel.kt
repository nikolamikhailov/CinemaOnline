package ru.l4gunner4l.cinemaonline.movielist.data.remote.model

import com.google.gson.annotations.SerializedName

data class GenreRemoteModel(
    @SerializedName("name")
    val name: String
)