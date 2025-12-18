package com.example.virtucare

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.virtucare.databinding.ActivityDoctorModuleAppointmentsBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DoctorModuleAppointments : AppCompatActivity() {

   lateinit var binding: ActivityDoctorModuleAppointmentsBinding
    lateinit var appointmentslist : ArrayList<appointmentDataModel>
    lateinit var databaseReference: DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)

        binding = ActivityDoctorModuleAppointmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backbutton.setOnClickListener {

            finish()
        }


        appointmentslist = arrayListOf<appointmentDataModel>()

        binding.recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        binding.recyclerview.setHasFixedSize(true)


        getdata()




    }

    private fun getdata() {


        databaseReference = FirebaseDatabase.getInstance().reference.child("Appointments")

        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()){

                    appointmentslist.clear()

                    for(appointsnap in snapshot.children){

                        val appoint = appointsnap.getValue(appointmentDataModel::class.java)
                        appoint?.let{

                            appointmentslist.add(it)

                        }

                    }

                    binding.recyclerview.adapter = DoAppointAdapter(this@DoctorModuleAppointments,appointmentslist)

                }


            }

            override fun onCancelled(error: DatabaseError) {

            }


        })



    }
}