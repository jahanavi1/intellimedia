package com.jahanavi.practical.remote

import android.content.Context
import android.widget.Toast
import com.jahanavi.practical.Url
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber
import java.io.IOException
import java.security.KeyManagementException
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException


open class BaseDataSource() {


    private fun setupRetrofit(url: String, context: Context): APIService? {

        var apiService: APIService? = null

        try {

            val interceptor = HttpLoggingInterceptor()
            interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
            val client = OkHttpClient.Builder().addInterceptor(BasicAuthInterceptor("admin", "admin")).build()

            val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()

            apiService = retrofit.create(APIService::class.java)


        } catch (e: CertificateException) {
            e.printStackTrace()
        } catch (e: KeyStoreException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        }

        return apiService
    }

    fun postCall(
        parameter: HashMap<String, String>,
        subUrl: HashMap<String, String>,
        URL_GET_DATA: String,
        context: Context, callback: ResponseCallback, requestCode: Int
    ) {
        val loginService = setupRetrofit(Url.BASE_URL, context)
        var loginCall: Call<String>? = null
        loginCall = loginService?.webServiceCallPOST(URL_GET_DATA, parameter,subUrl)
        Timber.d(loginCall?.request()?.url.toString())
        loginCall?.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                callback.onSuccess(response.body(), requestCode)
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(context, "Failure", Toast.LENGTH_SHORT).show()
                callback.onFailure(t.message, requestCode)
            }
        })

    }






}