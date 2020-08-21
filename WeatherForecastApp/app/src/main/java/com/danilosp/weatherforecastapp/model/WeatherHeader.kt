package com.danilosp.weatherforecastapp.model

import com.google.gson.annotations.SerializedName

class WeatherHeader (
    @SerializedName("message")
    var message : Int,

    @SerializedName("list")
    var list : List<WeatherDetail>
)