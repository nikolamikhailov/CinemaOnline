package ru.l4gunner4l.cinemaonline

import android.util.Log
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.l4gunner4l.cinemaonline.data.remote.HeaderInterceptor
import ru.l4gunner4l.cinemaonline.data.remote.MoviesApi
import ru.l4gunner4l.cinemaonline.data.remote.model.MovieModel
import ru.l4gunner4l.cinemaonline.data.remote.model.MoviesRemoteSource
import ru.l4gunner4l.cinemaonline.movielist.data.MoviesInteractor
import ru.l4gunner4l.cinemaonline.movielist.data.MoviesRepository
import ru.l4gunner4l.cinemaonline.movielist.data.MoviesRepositoryImpl
import ru.l4gunner4l.cinemaonline.movielist.ui.MoviesListViewModel
import ru.l4gunner4l.cinemaonline.player.ui.PlayerDelegate
import ru.l4gunner4l.cinemaonline.player.ui.PlayerDelegateImpl
import ru.l4gunner4l.cinemaonline.player.ui.PlayerViewModel
import ru.l4gunner4l.cinemaonline.singlemovie.ui.SingleMovieViewModel
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

const val MOVIES_QUALIFIER = "MOVIES_QUALIFIER"
const val USER_AGENT = "USER_AGENT"
private const val BASE_URL =
    "https://gist.githubusercontent.com/LukyanovAnatoliy/eca5141dedc79751b3d0b339649e06a3/raw/38f9419762adf27c34a3f052733b296385b6aa8d/"

val applicationModule = module {

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor(androidContext()))
            .addInterceptor(HttpLoggingInterceptor(
                object : HttpLoggingInterceptor.Logger {
                    override fun log(message: String) {
                        Log.d("OkHttp", message)
                    }
                }
            ).apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            })
            .build()
    }
}

val navModule = module {

    single<Cicerone<Router>>(named(MOVIES_QUALIFIER)) {
        Cicerone.create()
    }

    single<NavigatorHolder>(named(MOVIES_QUALIFIER)) {
        get<Cicerone<Router>>(named(MOVIES_QUALIFIER)).navigatorHolder
    }

    single<Router>(named(MOVIES_QUALIFIER)) {
        get<Cicerone<Router>>(named(MOVIES_QUALIFIER)).router
    }

}

val moviesListModule = module {
    viewModel<MoviesListViewModel> {
        MoviesListViewModel(get(), get(named(MOVIES_QUALIFIER)))
    }

    single<MoviesApi> {
        get<Retrofit>().create(MoviesApi::class.java)
    }

    single<MoviesRemoteSource> {
        MoviesRemoteSource(get())
    }

    single<MoviesRepository> {
        MoviesRepositoryImpl(get())
    }

    single<MoviesInteractor> {
        MoviesInteractor(get())
    }
}

val singleMovieModule = module {
    viewModel<SingleMovieViewModel> { (movie: MovieModel) ->
        SingleMovieViewModel(movie, get(named(MOVIES_QUALIFIER)))
    }
}


val playerModule = module {

    single<String>(named(USER_AGENT)) {
        Util.getUserAgent(
            get(),
            androidContext().getString(R.string.app_name)
        )
    }

    factory<ExoPlayer> {
        SimpleExoPlayer.Builder(androidContext()).build()
    }

    single<DefaultDataSourceFactory> {
        DefaultDataSourceFactory(
            get(),
            get<String>(named(USER_AGENT))
        )
    }

    factory<PlayerDelegate> {
        PlayerDelegateImpl(get(), get())
    }

    viewModel<PlayerViewModel> { (movie: MovieModel) ->
        PlayerViewModel(movie, get())
    }
}