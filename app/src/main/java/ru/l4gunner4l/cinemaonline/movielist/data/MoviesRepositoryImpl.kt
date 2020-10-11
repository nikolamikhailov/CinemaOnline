package ru.l4gunner4l.cinemaonline.movielist.data

import ru.l4gunner4l.cinemaonline.movielist.data.remote.model.MoviesRemoteSource
import ru.l4gunner4l.cinemaonline.movielist.ui.MovieListItem

class MoviesRepositoryImpl(private val remote: MoviesRemoteSource) : MoviesRepository {

    override suspend fun getMovies(): List<MovieListItem> {
        return remote.getMoviesResponse().movies.map {
            it.mapToUiModel()
        }
    }

}