package ru.l4gunner4l.cinemaonline.movielist.ui

import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.l4gunner4l.cinemaonline.base.BaseViewModel
import ru.l4gunner4l.cinemaonline.base.Event
import ru.l4gunner4l.cinemaonline.movielist.data.MoviesInteractor

class MoviesListViewModel(private val interactor: MoviesInteractor) : BaseViewModel<ViewState>() {

    override fun initialViewState(): ViewState = ViewState(STATUS.LOAD, emptyList())

    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is DataEvent.RequestMovies -> {
                GlobalScope.launch {
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
