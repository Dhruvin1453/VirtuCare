package com.example.virtucare

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.virtucare.databinding.ActivityAppointmentsBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class appointments : AppCompatActivity() {

    lateinit var binding : ActivityAppointmentsBinding
    lateinit var databaseReference: DatabaseReference
    lateinit var appointmentslist : ArrayList<appointmentDataModel>


    override fun onCreate(savedInstanceState: Bundle?) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)


        super.onCreate(savedInstanceState)

        binding = ActivityAppointmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        appointmentslist = ArrayList()

        binding.recycler.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
        binding.recycler.setHasFixedSize(true)

        getAppointments()

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

    private fun getAppointments() {

        databaseReference = FirebaseDatabase.getInstance().reference.child("Appointments")


        databaseReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    appointmentslist.clear()

                    for ( appoitSnap in snapshot.children){

                        val appoint = appoitSnap.getValue(appointmentDataModel::class.java)

                        appoint?.let{

                            appointmentslist.add(it)

                        }


                    }

                    binding.recycler.adapter = appointmentsadapter(this@appointments,appointmentslist)


                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })


    }
}