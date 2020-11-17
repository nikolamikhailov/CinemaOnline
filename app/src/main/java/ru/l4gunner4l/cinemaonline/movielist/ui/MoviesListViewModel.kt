package ru.l4gunner4l.cinemaonline.movielist.ui

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.l4gunner4l.cinemaonline.SingleMovieScreen
import ru.l4gunner4l.cinemaonline.base.BaseViewModel
import ru.l4gunner4l.cinemaonline.base.Event
import ru.l4gunner4l.cinemaonline.movielist.data.MoviesInteractor
import ru.terrakok.cicerone.Router

class MoviesListViewModel(
    private val interactor: MoviesInteractor,
    private val router: Router
) : BaseViewModel<ViewState>() {

    init {
        processDataEvent(DataEvent.RequestMovies)
    }

    override fun initialViewState(): ViewState = ViewState(STATUS.LOAD, emptyList())

    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {

            is UiEvent.OnItemClick -> {
                viewModelScope.launch {
                    router.navigateTo(SingleMovieScreen(previousState.moviesList[event.index]))
                }
            }

            is DataEvent.RequestMovies -> {
                viewModelScope.launch {
                    interactor.getMovies().fold(
                        { processDataEvent(DataEvent.FailureMoviesRequest) },
                        { processDataEvent(DataEvent.SuccessMoviesRequest(it)) }
                    )
                }
                return previousState.copy(status = STATUS.LOAD)
            }

            is DataEvent.SearchMovies -> {
                viewModelScope.launch {
                    val input = event.movieName
                    interactor.getMovies(input).fold(
                        { processDataEvent(DataEvent.FailureMoviesRequest) },
                        { processDataEvent(DataEvent.SuccessMoviesRequest(it)) }
                    )
                }
                return previousState.copy(status = STATUS.LOAD)
            }

            is DataEvent.SuccessMoviesRequest -> {
                return previousState.copy(
                    status = STATUS.CONTENT,
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
