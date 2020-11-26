package ru.l4gunner4l.cinemaonline.player.ui

import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import org.koin.core.KoinComponent

interface PlayerDelegate : KoinComponent {

    fun play()

    fun pause()

    fun preparePlayer(url: String)

    fun getPlayerImpl(): ExoPlayer

    fun releasePlayer()

    fun seekTo(position: Long)

    fun getPosition(): Long

    fun getState(): Int

    fun setStateListener(listener: Player.EventListener)

    fun removeAllListeners()

    fun removeListener(listener: Player.EventListener)
}