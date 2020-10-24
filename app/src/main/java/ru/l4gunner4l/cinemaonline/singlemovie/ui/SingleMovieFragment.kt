package ru.l4gunner4l.cinemaonline.singlemovie.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_single_movie.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.l4gunner4l.cinemaonline.R
import ru.l4gunner4l.cinemaonline.data.remote.model.MovieModel

class SingleMovieFragment : Fragment(R.layout.fragment_single_movie) {

    companion object {
        private const val KEY_MOVIE = "KEY_MOVIE"
        fun newInstance(movie: MovieModel) =
            SingleMovieFragment().apply {
                arguments = bundleOf(KEY_MOVIE to movie)
            }
    }

    private val viewModel: SingleMovieViewModel by viewModel {
        parametersOf(requireArguments().getParcelable(KEY_MOVIE))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState.observe(viewLifecycleOwner, Observer(::render))
        initUi()
    }

    private fun initUi() {
        openPlayerBtn.setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnWatchClick)
        }
    }

    private fun render(viewState: ViewState) {
        when (viewState.status) {
            STATUS.CONTENT -> {
                setMovieToUi(viewState.movie)
            }
            STATUS.LOAD -> {

            }
            STATUS.ERROR -> {

            }
        }
    }

    private fun setMovieToUi(movie: MovieModel) = with(movie) {
        toolbar.title = title
        descriptionTV.text = overview
        Glide.with(requireContext())
            .load(posterPath)
            .into(posterIV)
    }

}