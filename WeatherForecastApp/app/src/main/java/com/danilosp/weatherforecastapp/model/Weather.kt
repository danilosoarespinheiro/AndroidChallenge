package com.danilosp.weatherforecastapp.model

import com.google.gson.annotations.SerializedName

class Weather (

    @SerializedName("main")
    var main : String,

    @SerializedName("description")
    var description : String


)