package com.example.virtucare

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.virtucare.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth

class signup : AppCompatActivity() {


    lateinit var binding : ActivitySignupBinding
    lateinit var firebseauth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebseauth = FirebaseAuth.getInstance()

        binding.btnsign.setOnClickListener{

            val email : String = binding.email.text.toString()
            val pass : String = binding.pass.text.toString()
            val conpass : String = binding.conpass.text.toString()

            if ( email.isNotEmpty() && pass.isNotEmpty() && conpass.isNotEmpty()){

                if ( pass == conpass) {

                    firebseauth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener {

                        if(it.isSuccessful){

                            val intent = Intent(this, select_page::class.java)
                            startActivity(intent)
                            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)

                        }else{
                            Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }

                }else{

                    Toast.makeText(this,"Both Password Shold Be Same",Toast.LENGTH_SHORT).show()

                }

            }else{


                Toast.makeText(this,"Please Enter All the Value",Toast.LENGTH_SHORT).show()
            }


        }

        binding.loginbtn.setOnClickListener{

            val intent = Intent(this,login::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)

        }

    }
}