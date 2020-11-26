package ru.l4gunner4l.cinemaonline

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.*
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import ru.l4gunner4l.cinemaonline.base.toLeftEither
import ru.l4gunner4l.cinemaonline.base.toRightEither
import ru.l4gunner4l.cinemaonline.data.remote.model.GenreRemoteModel
import ru.l4gunner4l.cinemaonline.data.remote.model.MovieModel
import ru.l4gunner4l.cinemaonline.movielist.data.MoviesInteractor
import ru.l4gunner4l.cinemaonline.movielist.ui.DataEvent
import ru.l4gunner4l.cinemaonline.movielist.ui.MoviesListViewModel
import ru.l4gunner4l.cinemaonline.movielist.ui.STATUS
import ru.l4gunner4l.cinemaonline.movielist.ui.ViewState
import ru.terrakok.cicerone.Router

class MoviesListViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val coroutineRule = CoroutineRule()
    private lateinit var viewModel: MoviesListViewModel

    private val viewStateObserver: Observer<ViewState> = mock()
    private val interactor: MoviesInteractor = mock()
    private val router: Router = mock()

    @Test
    fun `test load status success`() {
        val m = MovieModel(
            isAdult = false,
            genres = listOf(GenreRemoteModel(name = "Drama")),
            id = 244786,
            originalLanguage = "en",
            originalTitle = "Whiplash",
            overview = "Under the direction of a ruthless instructor, a talented young drummer begins to pursue perfection at any cost, even his humanity.",
            releaseDate = "2014-10-10",
            posterPath = "https://upload.wikimedia.org/wikipedia/en/0/01/Whiplash_poster.jpg",
            popularity = 8.441533,
            title = "Whiplash",
            video = "http://techslides.com/demos/sample-videos/small.mp4, voteAverage=8.5, voteCount=856",
            voteAverage = 8.5,
            voteCount = 856
        )
        mockMovie(m)
        createViewModel()
        val vs = captureViewState()
        assertTrue(
            vs.status == STATUS.LOAD
        )
    }

    @Test
    fun `test request movies success`() {
        val m = MovieModel(
            isAdult = false,
            genres = listOf(GenreRemoteModel(name = "Drama")),
            id = 244786,
            originalLanguage = "en",
            originalTitle = "Whiplash",
            overview = "Under the direction of a ruthless instructor, a talented young drummer begins to pursue perfection at any cost, even his humanity.",
            releaseDate = "2014-10-10",
            posterPath = "https://upload.wikimedia.org/wikipedia/en/0/01/Whiplash_poster.jpg",
            popularity = 8.441533,
            title = "Whiplash",
            video = "http://techslides.com/demos/sample-videos/small.mp4, voteAverage=8.5, voteCount=856",
            voteAverage = 8.5,
            voteCount = 856
        )
        mockMovie(m)
        createViewModel()
        viewModel.processDataEvent(DataEvent.SuccessMoviesRequest(listOf(m)))
        val vs = captureViewState()
        assertTrue(
            vs.moviesList.first() == m
        )
    }

    @Test
    fun `test movies failure`() {
        createViewModel()
        mockMovieRequestError()
        val viewState = captureViewState()
    }


    private fun createViewModel() {
        viewModel = MoviesListViewModel(interactor, router)
        viewModel.viewState.observeForever(viewStateObserver)
    }

    private fun mockMovie(m: MovieModel) =
        runBlocking {
            whenever(interactor.getMovies())
                .thenReturn(listOf(m).toRightEither())
        }

    private fun mockMovieRequestError(throwable: Throwable = RuntimeException()) =
        runBlocking {
            whenever(interactor.getMovies())
                .thenReturn(throwable.toLeftEither())
        }

    private fun captureViewState(): ViewState = capture {
        verify(viewStateObserver, atLeastOnce()).onChanged(it.capture())
    }

}

inline fun <reified T : Any> capture(invokeCaptor: (KArgumentCaptor<T>) -> Unit): T {
    val captor = argumentCaptor<T>()
    invokeCaptor(captor)
    return captor.lastValue
}
