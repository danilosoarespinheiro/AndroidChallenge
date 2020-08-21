package com.danilosp.weatherforecastapp.model

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("forecast")
    fun getWeather(
        @Query("q") city: String,
        @Query("APPID")appid: String
    ): Single<WeatherHeader?>

}
