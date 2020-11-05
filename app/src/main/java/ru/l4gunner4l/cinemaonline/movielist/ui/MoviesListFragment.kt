package ru.l4gunner4l.cinemaonline.movielist.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import kotlinx.android.synthetic.main.fragment_movies_list.*
import kotlinx.android.synthetic.main.item_error.*
import kotlinx.android.synthetic.main.item_error.view.*
import kotlinx.android.synthetic.main.item_progress_bar.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.l4gunner4l.cinemaonline.R
import ru.l4gunner4l.cinemaonline.setAdapterAndCleanupOnDetachFromWindow
import ru.l4gunner4l.cinemaonline.setData

class MoviesListFragment : Fragment(R.layout.fragment_movies_list) {

    companion object {
        fun newInstance() = MoviesListFragment()
    }

    private val viewModel: MoviesListViewModel by viewModel()
    private val adapter = ListDelegationAdapter(
        moviesAdapterDelegate {
            viewModel.processUiEvent(UiEvent.OnItemClick(it))
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState.observe(viewLifecycleOwner, Observer(::render))
        viewModel.processDataEvent(DataEvent.RequestMovies)
        initView()
    }

    private fun initView() {
        rvMoviesList.layoutManager = LinearLayoutManager(requireContext())
        rvMoviesList.setAdapterAndCleanupOnDetachFromWindow(adapter)
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.processUiEvent(DataEvent.RefreshMovies)
        }
        errorItem.errorReload.setOnClickListener {
            viewModel.processDataEvent(DataEvent.RequestMovies)
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun render(viewState: ViewState) {
        when (viewState.status) {
            STATUS.LOAD -> {
                progress.isVisible = true
            }
            STATUS.ERROR -> {
                errorItem.errorText.text = "No Internet"
                errorItem.isVisible = true
                progress.isVisible = false
            }
            STATUS.CONTENT -> {
                errorItem.isVisible = false
                progress.isVisible = false
                adapter.setData(viewState.moviesList)
            }
            STATUS.ON_REFRESH -> {
                swipeRefreshLayout.isRefreshing = true
            }
            STATUS.REFRESHED -> {
                adapter.setData(viewState.moviesList)
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }
}
