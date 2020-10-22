package ru.l4gunner4l.cinemaonline.movielist.ui.model

import ru.l4gunner4l.cinemaonline.base.ListItem

data class MovieListItem(
    val id: Long,
    val title: String,
    val genres: List<GenreModel>,
    val overview: String,
    val image: String,
    val releaseDate: String
) : ListItem