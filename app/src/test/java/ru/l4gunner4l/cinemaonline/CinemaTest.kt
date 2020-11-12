package ru.l4gunner4l.cinemaonline

import android.os.Build
import android.os.Bundle
import android.os.Looper.getMainLooper
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import ru.l4gunner4l.cinemaonline.data.remote.model.GenreRemoteModel
import ru.l4gunner4l.cinemaonline.data.remote.model.MovieModel
import ru.l4gunner4l.cinemaonline.singlemovie.ui.SingleMovieFragment

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
@LooperMode(LooperMode.Mode.PAUSED)
class CinemaTest {

    @Test
    fun testEventFragment() {
        val fragmentArgs = Bundle().apply {
            putParcelable(
                "KEY_MOVIE", MovieModel(
                    isAdult = false,
                    genres = arrayListOf(GenreRemoteModel(name = "Drama")),
                    id = 244786L,
                    originalLanguage = "en",
                    originalTitle = "Whiplash",
                    overview = "Under the direction of a ruthless instructor, a talented young drummer begins to pursue perfection at any cost, even his humanity.",
                    releaseDate = "2014-10-10",
                    posterPath = "https://upload.wikimedia.org/wikipedia/en/0/01/Whiplash_poster.jpg",
                    popularity = 8.441533,
                    title = "Whiplash",
                    video = "http://techslides.com/demos/sample-videos/small.mp4",
                    voteAverage = 8.5,
                    voteCount = 856
                )
            )
        }
        launchFragmentInContainer<SingleMovieFragment>(
            fragmentArgs = fragmentArgs,
            themeResId = R.style.AppTheme
        )
        shadowOf(getMainLooper()).idle()
        onView(withId(R.id.openPlayerBtn)).perform(click())
    }
}