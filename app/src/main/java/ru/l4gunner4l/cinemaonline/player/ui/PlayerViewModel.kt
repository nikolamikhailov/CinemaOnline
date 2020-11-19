package ru.l4gunner4l.cinemaonline.player.ui

import android.util.Log
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.Player
import ru.l4gunner4l.cinemaonline.base.BaseViewModel
import ru.l4gunner4l.cinemaonline.base.Event
import ru.l4gunner4l.cinemaonline.data.remote.model.MovieModel

class PlayerViewModel(
    private val movie: MovieModel,
    private val playerDelegate: PlayerDelegate
) : BaseViewModel<ViewState>() {

    private var playerPosition: Long = 0

    init {
        playerDelegate.setStateListener(object : Player.EventListener {
            override fun onPlayerError(error: ExoPlaybackException) {
                when (error.type) {
                    ExoPlaybackException.TYPE_SOURCE -> {
                        processDataEvent(DataEvent.Error(PlayerExceptions.SourceException("Oh it's wrong: ${error.localizedMessage}")))
                    }
                    ExoPlaybackException.TYPE_RENDERER -> {
                        processDataEvent(DataEvent.Error(PlayerExceptions.RenderException("Oh it's wrong: ${error.localizedMessage}")))
                    }
                    ExoPlaybackException.TYPE_UNEXPECTED -> {
                        processDataEvent(DataEvent.Error(PlayerExceptions.UnexpectedException("Oh it's wrong: ${error.localizedMessage}")))
                    }
                }
            }
        })
    }

    override fun initialViewState(): ViewState {
        return ViewState(Status.Load, movie, playerDelegate.getPlayerImpl())
    }

    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is DataEvent.Load -> {
                playerDelegate.preparePlayer(movie.video)
                return previousState.copy(
                    status = Status.Content,
                    player = playerDelegate.getPlayerImpl()
                )
            }
            is DataEvent.Error -> {
                Log.i("M_MAIN", "Error = ${event.error}")
                return previousState.copy(
                    status = Status.Error(event.error)
                )
            }
            DataEvent.Play -> {
                if (playerDelegate.getState() == Player.STATE_ENDED) {
                    playerDelegate.seekTo(0)
                    playerPosition = playerDelegate.getPosition()
                }
                playerDelegate.seekTo(playerPosition)
                playerDelegate.play()
                return previousState
            }
            DataEvent.Pause -> {
                playerDelegate.pause()
                playerPosition = playerDelegate.getPosition()
                return previousState
            }
        }
        return null
    }
}