package com.example.virtucare

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.virtucare.databinding.ActivitySelectPageBinding

class select_page : AppCompatActivity() {

    lateinit var binding: ActivitySelectPageBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivitySelectPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.doctorselect.setOnClickListener {

            startActivity(Intent(this, RegitrationDoctor::class.java))
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)

        }


    }
}