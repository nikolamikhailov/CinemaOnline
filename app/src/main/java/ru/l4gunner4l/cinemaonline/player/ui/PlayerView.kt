package ru.l4gunner4l.cinemaonline.player.ui

import com.google.android.exoplayer2.ExoPlayer

interface PlayerView {

    fun setPlayer(player: ExoPlayer)

    fun showLoading(isLoading: Boolean)

    fun showError(message: String)
}