package ru.l4gunner4l.cinemaonline.player.ui

import ru.l4gunner4l.cinemaonline.base.Event
import ru.l4gunner4l.cinemaonline.data.remote.model.MovieModel


data class ViewState(
    val status: STATUS,
    val movie: MovieModel
)

sealed class UiEvent : Event

enum class STATUS {
    LOAD,
    CONTENT,
    ERROR
}