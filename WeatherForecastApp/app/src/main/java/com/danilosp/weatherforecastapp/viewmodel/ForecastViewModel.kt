package com.danilosp.weatherforecastapp.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.danilosp.weatherforecastapp.model.WeatherAPIService
import com.danilosp.weatherforecastapp.model.WeatherDetail
import com.danilosp.weatherforecastapp.model.WeatherHeader
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ForecastViewModel(application: Application) : AndroidViewModel(application) {

    var weathers: MutableLiveData<List<WeatherDetail>> = MutableLiveData()
    var weatherLoadError = MutableLiveData<Boolean>()
    var loading = MutableLiveData<Boolean>()
    var context = getApplication<Application>().applicationContext

    private val weatherAPIService: WeatherAPIService = WeatherAPIService(context)
    private val disposable = CompositeDisposable()

    fun fetchFromServer(city: String) {
        weatherAPIService.weatherAPIService()
        loading.value = true
        disposable.add(
            weatherAPIService.getWeather(city)
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribeWith(object :
                    DisposableSingleObserver<WeatherHeader>() {
                    override fun onSuccess(weatherHeader: WeatherHeader) {
                        Log.d("OK", "OK")
                        todosRetrieved(weatherHeader.list)
                    }

                    override fun onError(e: Throwable) {
                        weatherLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }
                })!!
        )
    }

    private fun todosRetrieved(weatherList: List<WeatherDetail>) {
        if(weatherList.isNotEmpty()){
            weathers.postValue(weatherList)
            weatherLoadError.postValue(false)

        }else{
            weatherLoadError.postValue(true)
        }
        loading.postValue(false)
    }

}
