package com.hakan.happybirthdayapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class MainActivity : AppCompatActivity() {


/**
 * SplashScreen ekranının hangi activityden önce açılacağı ve ekranda bekleme süresi
 * @author Hakan Akbudak
 * @since 08.09.2022
 * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed({
            val intent=Intent(this@MainActivity,SplashScreenActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)

    }

}