package ru.l4gunner4l.cinemaonline.player.ui

import android.util.Log
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.Player
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.l4gunner4l.cinemaonline.base.BaseViewModel
import ru.l4gunner4l.cinemaonline.base.Event
import ru.l4gunner4l.cinemaonline.data.remote.model.MovieModel

class PlayerViewModel(
    private val movie: MovieModel
) : BaseViewModel<ViewState>(), KoinComponent {

    private val player: PlayerDelegate by inject<PlayerDelegate>()

    override fun initialViewState(): ViewState {
        return ViewState(STATUS.LOAD, movie, player.getPlayerImpl())
    }

    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is DataEvent.Load -> {
                player.preparePlayer(movie.video)
                player.setStateListener(object : Player.EventListener {
                    override fun onLoadingChanged(isLoading: Boolean) {}
                    override fun onPlayerError(error: ExoPlaybackException) {
                        processDataEvent(DataEvent.Error(error.localizedMessage!!))
                    }
                })
                return previousState.copy(status = STATUS.CONTENT, player = player.getPlayerImpl())
            }
            is DataEvent.Error -> {
                Log.i("M_MAIN", "Error = ${event.textError}")
                return previousState.copy(status = STATUS.ERROR)
            }
            DataEvent.Play -> {
                if (player.getState() == Player.STATE_ENDED) {
                    player.seekTo(0)
                }
                player.pause()
                return previousState
            }
            DataEvent.Pause -> {
                player.pause()
                return previousState
            }
        }
        return null
    }
}