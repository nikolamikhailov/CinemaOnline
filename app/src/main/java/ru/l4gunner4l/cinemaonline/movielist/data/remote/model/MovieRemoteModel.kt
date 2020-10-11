package ru.l4gunner4l.cinemaonline.movielist.data.remote.model

import com.google.gson.annotations.SerializedName

data class MovieRemoteModel(
    @SerializedName("adult")
    val isAdult: Boolean,
    @SerializedName("genres")
    val genres: List<GenreRemoteModel>,
    @SerializedName("id")
    val id: Long,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("title")
    val title: String,
    @SerializedName("video")
    val video: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
)