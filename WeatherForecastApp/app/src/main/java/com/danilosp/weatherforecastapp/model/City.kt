package com.danilosp.weatherforecastapp.model

import com.google.gson.annotations.SerializedName

class City (

    @SerializedName("id")
    var id : Long,

    @SerializedName("name")
    var name : String,

    @SerializedName("country")
    var country : String
)