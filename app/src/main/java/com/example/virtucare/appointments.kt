package com.example.virtucare

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.virtucare.databinding.ActivityAppointmentsBinding

class appointments : AppCompatActivity() {

    lateinit var binding : ActivityAppointmentsBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)


        super.onCreate(savedInstanceState)

        binding = ActivityAppointmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.footer.setOnItemSelectedListener { item ->

            when(item.itemId){

                R.id.home -> {

                    startActivity(Intent(this, MainActivity::class.java))
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
                    true
                }

                R.id.doctors -> {

                    startActivity(Intent(this, DoctorList::class.java))
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
                    true
                }

                R.id.appointments-> {

                    startActivity(Intent(this, appointments::class.java))
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
                    true
                }


                else -> false

            }
        }

    }
}