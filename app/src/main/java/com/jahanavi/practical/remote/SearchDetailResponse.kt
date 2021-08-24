package com.jahanavi.practical.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


public class SearchDetailResponse  : Serializable{

    @SerializedName("records")
    @Expose
    var records: List<Records>? = null


}