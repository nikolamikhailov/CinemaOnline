package ru.l4gunner4l.cinemaonline.player.ui

import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player

interface PlayerDelegate {

    fun play()

    fun pause()

    fun preparePlayer(url: String)

    fun getPlayerImpl(): ExoPlayer

    fun releasePlayer()

    fun seekTo(position: Long)

    fun getState(): Int

    fun setStateListener(listener: Player.EventListener)

    fun removeListeners()
}