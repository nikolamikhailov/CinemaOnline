package ru.l4gunner4l.cinemaonline.singlemovie.ui

import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_single_movie.*
import kotlinx.android.synthetic.main.rating.*
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
        initUi()
        viewModel.viewState.observe(viewLifecycleOwner, Observer(::render))
    }

    private fun initUi() {
        val movie = requireArguments().getParcelable<MovieModel>(KEY_MOVIE)!!
        toolbar.navigationIcon = ResourcesCompat.getDrawable(
            resources,
            R.drawable.ic_back_24,
            requireContext().theme
        )
        toolbar.setNavigationOnClickListener {
            viewModel.processUiEvent(UiEvent.OnBackClick)
        }
        openPlayerBtn.setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnWatchClick)
        }
        movie.genres.forEach { genre ->
            genres.addView(
                Chip(requireContext()).apply {
                    text = genre.name.toLowerCase()
                    setChipBackgroundColorResource(R.color.colorPrimary)
                    setTextAppearanceResource(R.style.TextAppearance_Genre)
                }
            )
        }
        btnBack.setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnBackClick)
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
        ratingTV.text = voteAverage.toString()
        toolbar.title = title
        descriptionTV.text = overview
        Glide.with(requireContext())
            .load(posterPath)
            .into(posterIV)
    }

}