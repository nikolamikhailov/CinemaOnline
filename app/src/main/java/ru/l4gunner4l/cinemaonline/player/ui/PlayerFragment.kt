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
    private var isFirstLaunch = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState.observe(viewLifecycleOwner, Observer(::render))
        isFirstLaunch = savedInstanceState == null
        if (isFirstLaunch)
            viewModel.processDataEvent(DataEvent.Load)
        errorItem.errorReload.setOnClickListener {
            viewModel.processDataEvent(DataEvent.Load)
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.processDataEvent(DataEvent.Pause)
    }

    private fun render(viewState: ViewState) {
        when (viewState.status) {
            Status.Load -> {
                progress.isVisible = true
            }
            Status.Content -> {
                progress.isVisible = false
                errorItem.isVisible = false
                playerView.player = viewState.player
                if (isFirstLaunch)
                    viewModel.processDataEvent(DataEvent.Play)
            }
            is Status.Error -> {
                errorItem.isVisible = true
                when (viewState.status.error) {
                    is PlayerExceptions.SourceException -> {
                        errorItem.errorText.text = viewState.status.error.textError
                    }
                    else -> {
                        errorItem.errorText.text = getString(R.string.error_unknown)
                    }
                }

            }
        }
    }
}