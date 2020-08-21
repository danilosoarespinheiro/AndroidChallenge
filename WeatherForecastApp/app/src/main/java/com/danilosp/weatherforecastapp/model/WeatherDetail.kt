package com.danilosp.weatherforecastapp.model

import com.google.gson.annotations.SerializedName

class WeatherDetail (

    @SerializedName("dt")
    var dt : Long,

    @SerializedName("main")
    var main : WeatherMain,

    @SerializedName("weather")
    var weather : List<Weather>,

    @SerializedName("dt_txt")
    var dt_txt : String
)