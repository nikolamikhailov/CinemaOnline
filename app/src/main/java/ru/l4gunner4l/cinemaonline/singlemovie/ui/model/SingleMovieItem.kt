package ru.l4gunner4l.cinemaonline.singlemovie.ui.model

import ru.l4gunner4l.cinemaonline.movielist.data.remote.model.GenreRemoteModel

data class SingleMovieItem(
    val isAdult: Boolean,
    val genres: List<GenreRemoteModel>,
    val id: Long,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val releaseDate: String,
    val posterPath: String,
    val popularity: Double,
    val title: String,
    val video: String,
    val voteAverage: Double,
    val voteCount: Int
)