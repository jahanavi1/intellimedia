package com.jahanavi.practical.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Records : Serializable {
    @SerializedName("id")
    @Expose
    var id: Int? = 0

    @SerializedName("profile_pic")
    @Expose
    var profile_pic: String? = null

    @SerializedName("business_name")
    @Expose
    var business_name: String? = null

    @SerializedName("person_name")
    @Expose
    var person_name: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = ""

    @SerializedName("state")
    @Expose
    var state: String? = ""

    @SerializedName("country_id")
    @Expose
    var country_id: String? = null

    @SerializedName("state_id")
    @Expose
    var state_id: String? = ""

    @SerializedName("city_id")
    @Expose
    var city_id: String? = ""

    @SerializedName("country")
    @Expose
    var country: String? = ""


    @SerializedName("city")
    @Expose
    var city: String? = ""

    @SerializedName("address")
    @Expose
    var address: String? = ""

    @SerializedName("contact")
    @Expose
    var contact: String? = ""


    @SerializedName("zip_code")
    @Expose
    var zip_code: String? = ""

    @SerializedName("about_us")
    @Expose
    var about_us: String? = ""

    @SerializedName("c_latitude")
    @Expose
    var c_latitude: String? = ""


    @SerializedName("c_longitude")
    @Expose
    var c_longitude: String? = ""

    @SerializedName("distance_in_km")
    @Expose
    var distance_in_km: String? = ""

    @SerializedName("distance_in_miles")
    @Expose
    var distance_in_miles: String? = ""


    @SerializedName("distance_in_meter")
    @Expose
    var distance_in_meter: String? = ""

    @SerializedName("total_party_joined")
    @Expose
    var total_party_joined: String? = ""

    @SerializedName("total_party_animal")
    @Expose
    var total_party_animal: String? = ""


    @SerializedName("is_busiest_location")
    @Expose
    var is_busiest_location: String? = ""

    @SerializedName("is_event_nearest_me")
    @Expose
    var is_event_nearest_me: String? = ""

    @SerializedName("join_party_event")
    @Expose
    var join_party_event: String? = ""


    @SerializedName("is_favorite")
    @Expose
    var is_favorite: String? = ""

    @SerializedName("is_public_profile")
    @Expose
    var is_public_profile: String? = ""

    @SerializedName("avg_rating")
    @Expose
    var avg_rating: Int? = null


    @SerializedName("total_review")
    @Expose
    var total_review: String? = ""


}

