package com.danilosp.weatherforecastapp.model

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class WeatherAPIService(context: Context) {
    private val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    private val APPID = "b4257f902959e35846f9dd504af3be7a"

    private var api: WeatherAPI? = null

    val cachSize = (5 * 1024 * 1024).toLong()
    val myCache = okhttp3.Cache(context.cacheDir, cachSize)

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .cache(myCache)
        .addInterceptor { chain ->
            var request = chain.request()
            request = if (hasNetwork(context)!!)
                request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
            else
                request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
            chain.proceed(request)
        }
        .build()

    fun weatherAPIService() {
        api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(WeatherAPI::class.java)
    }

    fun hasNetwork(context: Context): Boolean? {
        var isConnected: Boolean? = false // Initial Value
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }

    fun getWeather(city: String): Single<WeatherHeader?>? {
        return api?.getWeather(city, APPID)
    }
}