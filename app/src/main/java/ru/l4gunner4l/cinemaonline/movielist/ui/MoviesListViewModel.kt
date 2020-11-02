package ru.l4gunner4l.cinemaonline.movielist.ui

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.l4gunner4l.cinemaonline.SingleMovieScreen
import ru.l4gunner4l.cinemaonline.base.BaseViewModel
import ru.l4gunner4l.cinemaonline.base.Event
import ru.l4gunner4l.cinemaonline.base.NoConnectionException
import ru.l4gunner4l.cinemaonline.movielist.data.MoviesInteractor
import ru.terrakok.cicerone.Router

class MoviesListViewModel(
    private val interactor: MoviesInteractor,
    private val router: Router
) : BaseViewModel<ViewState>() {

    override fun initialViewState(): ViewState = ViewState(STATUS.LOAD, emptyList())

    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is UiEvent.OnItemClick -> {
                viewModelScope.launch {
                    router.navigateTo(SingleMovieScreen(interactor.getMovies()[event.index]))
                }
            }
            is DataEvent.RequestMovies -> {
                viewModelScope.launch {
                    try {
                        val movies = interactor.getMovies()
                        processDataEvent(DataEvent.SuccessMoviesRequest(movies))
                    } catch (e: NoConnectionException) {
                        processDataEvent(DataEvent.FailureMoviesRequest)
                    }
                }
            }
            is DataEvent.SuccessMoviesRequest -> {
                return previousState.copy(
                    status = STATUS.CONTENT,
                    moviesList = event.movies
                )
            }
            is DataEvent.RefreshMovies -> {
                viewModelScope.launch {
                    val movies = interactor.getMovies()
                    processDataEvent(DataEvent.MoviesRefreshed(movies))
                }
                return previousState.copy(
                    status = STATUS.ON_REFRESH
                )
            }
            is DataEvent.MoviesRefreshed -> {
                return previousState.copy(
                    status = STATUS.REFRESHED,
                    moviesList = event.movies
                )
            }
            is DataEvent.FailureMoviesRequest -> {
                return previousState.copy(
                    status = STATUS.ERROR,
                    moviesList = emptyList()
                )
            }
        }
        return null
    }

}
