package ru.l4gunner4l.cinemaonline.movielist.ui

import android.util.Log
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

    override fun initialViewState(): ViewState = ViewState(STATUS.LOAD, emptyList())

    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is UiEvent.OnItemClick -> {
                viewModelScope.launch {
                    router.navigateTo(SingleMovieScreen(interactor.getMovies()[event.index].id))
                }
            }
            is DataEvent.RequestMovies -> {
                viewModelScope.launch {
                    val movies = interactor.getMovies()
                    Log.i("M_MAIN", movies.toString())
                    processDataEvent(DataEvent.SuccessMoviesRequest(movies))
                }
            }
            is DataEvent.SuccessMoviesRequest -> {
                return previousState.copy(
                    status = STATUS.CONTENT,
                    moviesList = event.movies
                )
            }
        }
        return null
    }

}
