package com.example.virtucare

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.virtucare.databinding.ActivityDetailactivityBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class detailactivity : AppCompatActivity() {

    lateinit var binding : ActivityDetailactivityBinding
    lateinit var item : doctordataModel
    lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityDetailactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        getBundle()



    }

    private fun getBundle() {
        item = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("object", doctordataModel::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("object")
        }!!

        binding.apply {


            tvDname.text = item.name
            tvspecial.text = item.specialization
            tvExperience.text = item.experience
            tvBio.text = item.bio
            tvAdress.text = item.email

            backbutton.setOnClickListener {
                finish()
            }

            btnappointments.setOnClickListener {


                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)


                val datePicker = DatePickerDialog(this@detailactivity,{_,selectedYear,selectedMonth,selectedDay ->

                    val hour = calendar.get(Calendar.HOUR_OF_DAY)
                    val minute = calendar.get(Calendar.MINUTE)

                    // Step 2: Time Picker
                    val timePicker = TimePickerDialog(this@detailactivity, { _, selectedHour, selectedMinute ->
                        // Format date and time
                        val date = "${selectedDay}/${selectedMonth + 1}/$selectedYear"
                        val time = String.format("%02d:%02d", selectedHour, selectedMinute)



                        val name : String = "Niraj Chauhan"
                        val email : String = "niraj67@gmail.com"
                        val doctor : String = "joel shah"
                        val mobile : String = "838393788"



                        val appoinmet = appointmentDataModel(name,email,doctor,mobile,date,time)



                        databaseReference = FirebaseDatabase.getInstance().reference.child("Appointments").child("testid02")

                        databaseReference.setValue(appoinmet).addOnCompleteListener {

                            Toast.makeText(this@detailactivity, "Appointment Booked  successfully!", Toast.LENGTH_SHORT).show()
                        }
                            .addOnFailureListener {
                                Toast.makeText(this@detailactivity, "Failed to Book Appintments: ${it.message}", Toast.LENGTH_SHORT).show()
                            }




                    }, hour, minute, true)

                    timePicker.show()

                }, year, month, day)

                datePicker.show()





            }






        }

    }

}