package ru.l4gunner4l.cinemaonline

import android.os.Build
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hannesdorfmann.adapterdelegates4.dsl.AdapterDelegateLayoutContainerViewHolder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import ru.l4gunner4l.cinemaonline.data.remote.model.MovieModel

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class CinemaSpec {

    /*@Before
    fun setup() {
        launchFragmentInContainer<MoviesListFragment>(themeResId = R.style.AppTheme)
        //launchFragmentInContainer<SingleMovieFragment>(themeResId = R.style.AppTheme)
    }*/

    @Rule
    var activityScenarioRule = ActivityScenarioRule(
        MainActivity::class.java
    )

    @Test
    fun itemsClick() {
        onView(ViewMatchers.withId(R.id.rvMoviesList))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<AdapterDelegateLayoutContainerViewHolder<MovieModel>>(
                    0,
                    click()
                )
            )
        //onView(ViewMatchers.withId(android.R.id.))
        /*onView(ViewMatchers.withId(R.id.rvMoviesList))
            .perform(
                RecyclerViewActions.scrollToPosition<AdapterDelegateLayoutContainerViewHolder<MovieModel>>(2)
            )*/

    }

}