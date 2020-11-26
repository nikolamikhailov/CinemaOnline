package ru.l4gunner4l.cinemaonline.movielist.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import kotlinx.android.synthetic.main.fragment_movies_list.*
import kotlinx.android.synthetic.main.item_error.*
import kotlinx.android.synthetic.main.item_error.view.*
import kotlinx.android.synthetic.main.item_progress_bar.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.l4gunner4l.cinemaonline.R
import ru.l4gunner4l.cinemaonline.base.setAdapterAndCleanupOnDetachFromWindow
import ru.l4gunner4l.cinemaonline.base.setData
import ru.l4gunner4l.cinemaonline.data.remote.model.MovieModel

class MoviesListFragment : Fragment(R.layout.fragment_movies_list) {

    companion object {
        fun newInstance() = MoviesListFragment()
    }

    private val handler: Handler by lazy { Handler(Looper.myLooper()!!) }
    private val viewModel: MoviesListViewModel by viewModel()
    private val adapter = ListDelegationAdapter(
        moviesAdapterDelegate {
            viewModel.processUiEvent(UiEvent.OnItemClick(it))
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewModel.viewState.observe(viewLifecycleOwner, Observer(::render))
    }

    private fun initView() {
        rvMoviesList.layoutManager = LinearLayoutManager(requireContext())
        rvMoviesList.addItemDecoration(DividerItemDecoration(activity, RecyclerView.VERTICAL))
        rvMoviesList.setAdapterAndCleanupOnDetachFromWindow(adapter)
        swipeRefreshLayout.setOnRefreshListener {
            onRefresh()
        }
        errorItem.errorReload.setOnClickListener {
            onReload()
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                handler.postDelayed({
                    requestOrSearch(newText)
                }, 1000)
                return true
            }
        })
    }

    private fun onReload() {
        requestOrSearch(searchView.query.toString())
    }

    private fun onRefresh() {
        requestOrSearch(searchView.query.toString())
    }

    private fun requestOrSearch(name: String?) {
        if (name != null && name.isNotBlank())
            viewModel.processDataEvent(DataEvent.SearchMovies(searchView.query.toString()))
        else viewModel.processUiEvent(DataEvent.RequestMovies)
    }

    private fun render(viewState: ViewState) {
        when (viewState.status) {
            STATUS.LOAD -> {
                setLoadUIMode()
            }
            STATUS.ERROR -> {
                setErrorUIMode()
            }
            STATUS.CONTENT -> {
                adapter.setData(viewState.moviesList)
                setContentUIMode(viewState.moviesList)
            }
        }
    }

    private fun setLoadUIMode() {
        splashScreen?.isVisible = true
        progress?.isVisible = true
    }

    private fun setContentUIMode(movies: List<MovieModel>) {
        rvMoviesList.isVisible = movies.isNotEmpty()
        noMoviesTV.isVisible = movies.isEmpty()
        errorItem.isVisible = false
        splashScreen.isVisible = false
        progress.isVisible = false
        searchView.isVisible = true
        swipeRefreshLayout.isRefreshing = false
    }

    private fun setErrorUIMode() {
        searchView.isVisible = false
        errorItem.errorText.text = getString(R.string.error_no_internet)
        errorItem.isVisible = true
        splashScreen.isVisible = true
        progress.isVisible = false
        rvMoviesList.isVisible = false
        swipeRefreshLayout.isRefreshing = false
    }
}
