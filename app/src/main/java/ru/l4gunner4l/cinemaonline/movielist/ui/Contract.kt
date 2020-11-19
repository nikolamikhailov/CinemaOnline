package ru.l4gunner4l.cinemaonline.movielist.ui

import ru.l4gunner4l.cinemaonline.base.Event
import ru.l4gunner4l.cinemaonline.data.remote.model.MovieModel

data class ViewState(
    val status: STATUS,
    val moviesList: List<MovieModel>
)

sealed class UiEvent : Event {
    data class OnItemClick(val index: Int) : UiEvent()
}

sealed class DataEvent : Event {
    object RequestMovies : DataEvent()
    data class SearchMovies(val movieName: String) : DataEvent()
    data class SuccessMoviesRequest(val movies: List<MovieModel>) : DataEvent()
    object FailureMoviesRequest : DataEvent()
}

enum class STATUS {
    LOAD,
    CONTENT,
    ERROR
}