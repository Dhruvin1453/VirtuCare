package com.example.virtucare

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.virtucare.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    lateinit var databaseReference: DatabaseReference
    lateinit var databaseReference2: DatabaseReference
    lateinit var databaseReference3: DatabaseReference
    lateinit var databaseReference4: DatabaseReference
    lateinit var databaseReference5: DatabaseReference
    lateinit var doctorlist: ArrayList<doctordataModel>
    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)


        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerview.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerview.setHasFixedSize(true)

        doctorlist = arrayListOf<doctordataModel>()


        getDoctordata()






      /* default data for testing ----------------------------------------------------------------------------------------------------------------------


              val defualtuserid = "testid01"

        databaseReference =
            FirebaseDatabase.getInstance().reference.child("Doctors").child(defualtuserid)

        val doctor = doctordataModel(

            name = "Dr. John Doe",
            specilization = "Cardiologist",
            experience = "10",
            bio = "Experienced heart specialist",
            mobile = "1234567890",
            email = "johndoe@example.com",
            imageurl = "https://example.com/image.jpg"

        )


        databaseReference.setValue(doctor).addOnCompleteListener {

            Toast.makeText(this, "Doctor added successfully!", Toast.LENGTH_SHORT).show()
        }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to add doctor: ${it.message}", Toast.LENGTH_SHORT)
                    .show()
            } */


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

                R.id.profile -> {
                    startActivity(Intent(this, DoctorModuleAppointments::class.java))
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
                    true
                }


                else -> false

            }
        }






    }

    private fun getDoctordata() {

        databaseReference = FirebaseDatabase.getInstance().reference.child("Doctors")

        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {

                    doctorlist.clear()

                    for (doctorSnap in snapshot.children) {
                        val doctor = doctorSnap.getValue(doctordataModel::class.java)
                        doctor?.let {
                            doctorlist.add(it)
                        }
                    }

                    binding.recyclerview.adapter = topdoctoradapter(this@MainActivity,doctorlist)
                }


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })


    }
}