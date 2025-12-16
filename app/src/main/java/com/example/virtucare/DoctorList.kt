package com.example.virtucare

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.virtucare.databinding.ActivityDoctorlistBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DoctorList : AppCompatActivity() {


    lateinit var databaseReference: DatabaseReference

    lateinit var doctorlist: ArrayList<doctordataModel>
    lateinit var binding : ActivityDoctorlistBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)

        binding = ActivityDoctorlistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerview.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
        binding.recyclerview.setHasFixedSize(true)

        doctorlist = arrayListOf<doctordataModel>()


        getDoctordata()

        setupRecyclerView()





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

    private lateinit var adapter: doctorAdapter

    private fun setupRecyclerView() {
        adapter = doctorAdapter(doctorlist)
        binding.recyclerview.adapter = adapter
    }

    private fun getDoctordata() {
        databaseReference = FirebaseDatabase.getInstance().reference.child("Doctors")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                doctorlist.clear()
                if (snapshot.exists()) {
                    for (doctorSnap in snapshot.children) {
                        val doctor = doctorSnap.getValue(doctordataModel::class.java)
                        doctor?.let { doctorlist.add(it) }
                    }
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

}