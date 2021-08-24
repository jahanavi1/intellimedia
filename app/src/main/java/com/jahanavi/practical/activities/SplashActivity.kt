package com.jahanavi.practical.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.jahanavi.practical.BindingActivity
import com.jahanavi.practical.R
import com.jahanavi.practical.databinding.ActivitySplashBinding


class SplashActivity() : BindingActivity<ActivitySplashBinding>() {
    val TAG = this::class.java.simpleName
    var handler: Handler? = null
    override fun getLayoutResId() = R.layout.activity_splash


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handler = Handler(Looper.getMainLooper())
      handler!!.postDelayed({
          val intent = Intent(this@SplashActivity, MainActivity::class.java)
          startActivity(intent)
          finish()
      }, 3000)
    }
}


