package ru.l4gunner4l.cinemaonline.data.remote.model

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val movies: List<MovieModel>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)