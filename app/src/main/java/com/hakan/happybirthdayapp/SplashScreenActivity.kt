package com.hakan.happybirthdayapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hakan.happybirthdayapp.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}