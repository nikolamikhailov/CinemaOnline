package ru.l4gunner4l.cinemaonline.singlemovie.ui

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.l4gunner4l.cinemaonline.base.BaseViewModel
import ru.l4gunner4l.cinemaonline.base.Event
import ru.l4gunner4l.cinemaonline.singlemovie.data.SingleMovieInteractor
import ru.terrakok.cicerone.Router

class SingleMovieViewModel(
    private val id: Long,
    private val interactor: SingleMovieInteractor,
    private val router: Router
) : BaseViewModel<ViewState>() {

    init {
        processDataEvent(DataEvent.RequestMovie(id))
    }

    override fun initialViewState(): ViewState {
        return ViewState(STATUS.LOAD, null)
    }

    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is DataEvent.RequestMovie -> {
                viewModelScope.launch {
                    val movie = interactor.getMovie(id)
                    processDataEvent(DataEvent.SuccessMovieRequest(movie))
                }
            }
            is DataEvent.SuccessMovieRequest -> {
                return previousState.copy(
                    status = STATUS.CONTENT,
                    movie = event.movieModel
                )
            }

        }
        return null
    }
}
