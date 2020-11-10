package ru.l4gunner4l.cinemaonline.player.ui

sealed class PlayerExceptions {
    data class SourceException(val textError: String) : PlayerExceptions()
}