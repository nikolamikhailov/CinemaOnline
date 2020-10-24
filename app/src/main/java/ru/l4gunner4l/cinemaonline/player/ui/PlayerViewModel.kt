package ru.l4gunner4l.cinemaonline.player.ui

import ru.l4gunner4l.cinemaonline.base.BaseViewModel
import ru.l4gunner4l.cinemaonline.base.Event
import ru.l4gunner4l.cinemaonline.data.remote.model.MovieModel
import ru.terrakok.cicerone.Router

class PlayerViewModel(
    private val movie: MovieModel,
    private val router: Router
) : BaseViewModel<ViewState>() {

    override fun initialViewState(): ViewState {
        return ViewState(STATUS.CONTENT, movie)
    }

    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {

        }
        return null
    }
}