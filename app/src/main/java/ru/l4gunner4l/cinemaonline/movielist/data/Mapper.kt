package ru.l4gunner4l.cinemaonline.movielist.data

import ru.l4gunner4l.cinemaonline.movielist.data.remote.model.GenreRemoteModel
import ru.l4gunner4l.cinemaonline.movielist.data.remote.model.MovieRemoteModel
import ru.l4gunner4l.cinemaonline.movielist.ui.model.GenreModel
import ru.l4gunner4l.cinemaonline.movielist.ui.model.MovieListItem
import ru.l4gunner4l.cinemaonline.singlemovie.ui.model.SingleMovieItem

fun MovieRemoteModel.mapToUiListItemModel(): MovieListItem {
    return MovieListItem(
        id = id,
        title = title,
        genres = genres.map { it.mapToUiModel() },
        overview = overview,
        image = posterPath,
        releaseDate = releaseDate
    )
}

fun MovieRemoteModel.mapToUiSingleModel(): SingleMovieItem {
    return SingleMovieItem(
        isAdult = isAdult,
        genres = genres,
        id = id,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        releaseDate = releaseDate,
        posterPath = posterPath,
        popularity = popularity,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}

fun GenreRemoteModel.mapToUiModel(): GenreModel {
    return GenreModel(name)
}