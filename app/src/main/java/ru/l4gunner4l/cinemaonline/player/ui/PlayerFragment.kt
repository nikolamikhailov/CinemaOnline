package ru.l4gunner4l.cinemaonline.player.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_player.*
import kotlinx.android.synthetic.main.item_error.*
import kotlinx.android.synthetic.main.item_error.view.*
import kotlinx.android.synthetic.main.item_progress_bar.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.l4gunner4l.cinemaonline.R
import ru.l4gunner4l.cinemaonline.data.remote.model.MovieModel

class PlayerFragment : Fragment(R.layout.fragment_player) {

    companion object {
        private const val KEY_MOVIE = "KEY_MOVIE"
        fun newInstance(movie: MovieModel) =
            PlayerFragment().apply {
                arguments = bundleOf(KEY_MOVIE to movie)
            }
    }

    private val viewModel: PlayerViewModel by viewModel {
        parametersOf(requireArguments().getParcelable(KEY_MOVIE))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState.observe(viewLifecycleOwner, Observer(::render))
        progress.isVisible = true
    }

    override fun onResume() {
        super.onResume()
        viewModel.processDataEvent(DataEvent.Play)
    }

    override fun onStop() {
        super.onStop()
        viewModel.processDataEvent(DataEvent.Pause)
    }

    private fun render(viewState: ViewState) {
        when (viewState.status) {
            STATUS.LOAD -> {
                playerView.player = viewState.player
                progress.isVisible = false
            }
            STATUS.CONTENT -> {
                setMovieToUi(viewState.movie)
            }
            STATUS.ERROR -> {
                errorItem.isVisible = true
                errorItem.errorText.text = "Error"
            }
        }
    }

    private fun setMovieToUi(movie: MovieModel) {

    }

}