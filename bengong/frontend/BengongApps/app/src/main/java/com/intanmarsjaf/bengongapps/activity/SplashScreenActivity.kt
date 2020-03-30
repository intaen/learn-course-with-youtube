package com.intanmarsjaf.bengongapps.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.intanmarsjaf.bengongapps.R

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        Handler().postDelayed ({
            startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
            finish()
        },3000)
    }
}
