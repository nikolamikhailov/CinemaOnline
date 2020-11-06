package ru.l4gunner4l.cinemaonline.player.ui

import android.net.Uri
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import org.koin.core.KoinComponent

class PlayerDelegateImpl(
    private val player: ExoPlayer,
    private val dataSourceFactory: DefaultDataSourceFactory
) : PlayerDelegate, KoinComponent {

    private val listeners: MutableSet<Player.EventListener> = mutableSetOf()

    override fun play() {
        player.playWhenReady = true
    }

    override fun pause() {
        player.playWhenReady = false
    }

    override fun preparePlayer(url: String) {
        ProgressiveMediaSource
            .Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(Uri.parse(url)))
            .also { mediaSource ->
                player.setMediaSource(mediaSource)
                player.prepare()
            }
    }

    override fun getPlayerImpl(): ExoPlayer = player

    override fun releasePlayer() {
        player.stop()
        player.release()
    }

    override fun seekTo(position: Long) {
        player.seekTo(position)
    }

    override fun getState(): Int = player.playbackState

    override fun setStateListener(listener: Player.EventListener) {
        player.addListener(listener)
        listeners.add(listener)
    }

    override fun removeListeners() {
        listeners.forEach {
            player.removeListener(it)
        }
    }

}