package com.jahanavi.practical.remote


interface ResponseCallback {
    fun onSuccess(result: String?, requestCode: Int)

    fun onFailure(message: String?, requestCode: Int)
}

