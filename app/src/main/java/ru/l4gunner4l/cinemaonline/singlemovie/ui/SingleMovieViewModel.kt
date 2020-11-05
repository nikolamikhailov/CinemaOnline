package ru.l4gunner4l.cinemaonline.singlemovie.ui

import ru.l4gunner4l.cinemaonline.PlayerScreen
import ru.l4gunner4l.cinemaonline.base.BaseViewModel
import ru.l4gunner4l.cinemaonline.base.Event
import ru.l4gunner4l.cinemaonline.data.remote.model.MovieModel
import ru.terrakok.cicerone.Router

class SingleMovieViewModel(
    private val movie: MovieModel,
    private val router: Router
) : BaseViewModel<ViewState>() {

    override fun initialViewState(): ViewState {
        return ViewState(STATUS.CONTENT, movie)
    }

    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is UiEvent.OnWatchClick -> {
                router.navigateTo(PlayerScreen(movie))
            }
            is UiEvent.OnBackClick -> {
                router.exit()
            }
        }
        return null
    }
}
