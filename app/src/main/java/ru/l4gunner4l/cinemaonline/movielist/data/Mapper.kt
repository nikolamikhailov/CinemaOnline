package ru.l4gunner4l.cinemaonline.movielist.data

import ru.l4gunner4l.cinemaonline.movielist.data.remote.model.GenreRemoteModel
import ru.l4gunner4l.cinemaonline.movielist.data.remote.model.MovieRemoteModel
import ru.l4gunner4l.cinemaonline.movielist.ui.GenreModel
import ru.l4gunner4l.cinemaonline.movielist.ui.MovieListItem

fun MovieRemoteModel.mapToUiModel(): MovieListItem {
    return MovieListItem(
        id = id,
        title = title,
        genres = genres.map { it.mapToUiModel() },
        overview = overview,
        image = posterPath,
        releaseDate = releaseDate
    )
}

fun GenreRemoteModel.mapToUiModel(): GenreModel {
    return GenreModel(name)
}