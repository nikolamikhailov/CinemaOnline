package ru.l4gunner4l.cinemaonline.base

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder =
            chain.request().newBuilder().addHeader("x-api-key", "4f26925b85824439a8d15410472beff9")
        return chain.proceed(builder.build())
    }

}