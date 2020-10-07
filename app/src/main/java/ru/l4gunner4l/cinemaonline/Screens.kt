package ru.l4gunner4l.cinemaonline

import androidx.fragment.app.Fragment
import ru.l4gunner4l.cinemaonline.movielist.ui.MoviesListFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class MoviesScreen : SupportAppScreen() {
    override fun getFragment(): Fragment? {
        return MoviesListFragment.newInstance()
    }
}