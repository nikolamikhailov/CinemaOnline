package ru.l4gunner4l.cinemaonline.movielist.data

import ru.l4gunner4l.cinemaonline.movielist.data.remote.model.MoviesRemoteSource
import ru.l4gunner4l.cinemaonline.movielist.ui.model.MovieListItem
import ru.l4gunner4l.cinemaonline.singlemovie.ui.model.SingleMovieItem

class MoviesRepositoryImpl(private val remote: MoviesRemoteSource) : MoviesRepository {

    override suspend fun getMovies(): List<MovieListItem> {
        return remote.getMoviesResponse().movies.map {
            it.mapToUiListItemModel()
        }
    }

    override suspend fun getMovie(id: Long): SingleMovieItem {
        return remote.getMoviesResponse().movies.find {
            it.id == id
        }!!.mapToUiSingleModel()
    }

}