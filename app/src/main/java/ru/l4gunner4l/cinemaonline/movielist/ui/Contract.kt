package ru.l4gunner4l.cinemaonline.movielist.ui

import ru.l4gunner4l.cinemaonline.base.Event

data class ViewState(
    val status: STATUS,
    val moviesList: List<MovieListItem>
)

sealed class UiEvent() : Event {
    data class OnItemClick(val index: Int) : UiEvent()
}

sealed class DataEvent() : Event {
    object RequestMovies : DataEvent()
    data class SuccessMoviesRequest(val movies: List<MovieListItem>) : DataEvent()
}

enum class STATUS {
    LOAD,
    CONTENT,
    ERROR
}