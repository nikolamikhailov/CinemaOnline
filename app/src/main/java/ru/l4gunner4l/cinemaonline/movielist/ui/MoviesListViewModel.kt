package ru.l4gunner4l.cinemaonline.movielist.ui

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.l4gunner4l.cinemaonline.SingleMovieScreen
import ru.l4gunner4l.cinemaonline.base.BaseViewModel
import ru.l4gunner4l.cinemaonline.base.Event
import ru.l4gunner4l.cinemaonline.base.NoConnectionException
import ru.l4gunner4l.cinemaonline.data.remote.model.MovieModel
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
                        val movies: List<MovieModel> = interactor.getMovies()
                        processDataEvent(DataEvent.SuccessMoviesRequest(movies))
                    } catch (e: NoConnectionException) {
                        processDataEvent(DataEvent.FailureMoviesRequest)
                    }
                }
                if (previousState.status == STATUS.CONTENT) {
                    return previousState.copy(status = STATUS.LOAD, moviesList = emptyList())
                }
            }

            is DataEvent.SearchMovies -> {
                viewModelScope.launch {
                    try {
                        val input = event.movieName.trim().toLowerCase()
                        val movies = if (input.isBlank()) interactor.getMovies()
                        else interactor.getMovies(input)
                        processDataEvent(DataEvent.SuccessMoviesRequest(movies))
                    } catch (e: NoConnectionException) {
                        processDataEvent(DataEvent.FailureMoviesRequest)
                    }
                }
                if (previousState.status == STATUS.CONTENT) {
                    return previousState.copy(status = STATUS.LOAD, moviesList = emptyList())
                }
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
