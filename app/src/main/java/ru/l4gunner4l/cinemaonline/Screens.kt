package ru.l4gunner4l.cinemaonline

import androidx.fragment.app.Fragment
import ru.l4gunner4l.cinemaonline.data.remote.model.MovieModel
import ru.l4gunner4l.cinemaonline.movielist.ui.MoviesListFragment
import ru.l4gunner4l.cinemaonline.player.ui.PlayerFragment
import ru.l4gunner4l.cinemaonline.singlemovie.ui.SingleMovieFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class MoviesScreen : SupportAppScreen() {
    override fun getFragment(): Fragment? {
        return MoviesListFragment.newInstance()
    }
}

class SingleMovieScreen(private val movie: MovieModel) : SupportAppScreen() {
    override fun getFragment(): Fragment? {
        return SingleMovieFragment.newInstance(movie)
    }
}

class PlayerScreen(private val movie: MovieModel) : SupportAppScreen() {
    override fun getFragment(): Fragment? {
        return PlayerFragment.newInstance(movie)
    }
}