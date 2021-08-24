package com.jahanavi.practical.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class SearchData {
    @SerializedName("message")
    @Expose
    var message: Any? = ""

    @SerializedName("data")
    @Expose
    var data: SearchDetailResponse = SearchDetailResponse()
}