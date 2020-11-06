package ru.l4gunner4l.cinemaonline.player.ui

import com.google.android.exoplayer2.ExoPlayer
import ru.l4gunner4l.cinemaonline.base.Event
import ru.l4gunner4l.cinemaonline.data.remote.model.MovieModel


data class ViewState(
    val status: STATUS,
    val movie: MovieModel,
    val player: ExoPlayer
)

sealed class DataEvent : Event {
    object Load : DataEvent()
    data class Error(val error: Exception) : DataEvent()
    object Play : DataEvent()
    object Pause : DataEvent()
}
sealed class UiEvent : Event {
    object OnExitClick : UiEvent()
}

enum class STATUS {
    LOAD,
    CONTENT,
    ERROR
}