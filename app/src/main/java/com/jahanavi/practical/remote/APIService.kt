package com.jahanavi.practical.remote

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*




interface APIService {

    @POST("validate-user-app-auth-data")
    suspend fun userLogin(email_address: String, password: String): Response<String>

    @GET
    fun webServiceCall(
        @Url suburl: String,
        @QueryMap(encoded = true) param: Map<String, String>
    ): Call<String>

    @GET
    fun webServiceCall(
        @Url suburl: String
    ): Call<String>

    @FormUrlEncoded
    @POST
    fun webServiceCallPOST(
        @Url suburl: String,
        @FieldMap(encoded = true) params: Map<String, String>,
        @FieldMap(encoded = true) param: Map<String, String>
    ): Call<String>

    @FormUrlEncoded
    @POST("Accounts/{ACCOUNT_SID}/SMS/Messages")
    fun sendMessage(
        @Path("ACCOUNT_SID") accountSId: String,
        @Header("Authorization") signature: String,
        @FieldMap metadata: Map<String, String>
    ): Call<String>

}

