package ru.l4gunner4l.cinemaonline.singlemovie.ui

import ru.l4gunner4l.cinemaonline.base.Event
import ru.l4gunner4l.cinemaonline.singlemovie.ui.model.SingleMovieItem


data class ViewState(
    val status: STATUS,
    val movie: SingleMovieItem?
)

sealed class UiEvent : Event {
    object OnWatchClick : UiEvent()
}

sealed class DataEvent : Event {
    data class RequestMovie(val id: Long) : DataEvent()
    data class SuccessMovieRequest(val movieModel: SingleMovieItem) : DataEvent()
}

enum class STATUS {
    LOAD,
    CONTENT,
    ERROR
}