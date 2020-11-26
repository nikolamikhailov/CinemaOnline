package ru.l4gunner4l.cinemaonline.data.remote

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class HeaderInterceptor(val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected()) throw NoConnectionException()
        val builder =
            chain.request().newBuilder().addHeader("x-api-key", "4f26925b85824439a8d15410472beff9")
        return chain.proceed(builder.build())
    }

    private fun isConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }

}

class NoConnectionException : IOException() {
    override val message: String?
        get() = "No Internet Connection"
}