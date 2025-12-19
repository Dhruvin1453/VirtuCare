package com.example.virtucare



import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.virtucare.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class login : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var firebseauth: FirebaseAuth
    lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)


        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebseauth = FirebaseAuth.getInstance()


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.btnlogin.setOnClickListener {
            val email: String = binding.email.text.toString()
            val pass: String = binding.pass.text.toString()


            if (email.isNotEmpty() && pass.isNotEmpty()) {


                firebseauth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {

                    if (it.isSuccessful) {

                        val intent = Intent(this, select_page::class.java)
                        startActivity(intent)
                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)

                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }


            } else {

                Toast.makeText(this, "Please Enter All the Value", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnsign.setOnClickListener {

            val intent = Intent(this, signup::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
        }

        binding.googlesignin.setOnClickListener {

            signingoogle()

        }


    }


    private fun signingoogle(){

        val signInIntent = googleSignInClient.signInIntent


        launcher.launch(signInIntent)

    }


    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

            result ->

        if(result.resultCode == Activity.RESULT_OK){

            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResult(task)
        }
    }


    private fun handleResult(task: Task<GoogleSignInAccount>){

        if(task.isSuccessful){

            val account : GoogleSignInAccount ? = task.result
            if (account != null){

                updateUI(account)

            }
        }else{

            Toast.makeText(this,task.exception.toString(),Toast.LENGTH_SHORT).show()
        }

    }

    private fun updateUI(account: GoogleSignInAccount) {

        val credentials = GoogleAuthProvider.getCredential(account.idToken,null)
        firebseauth.signInWithCredential(credentials).addOnCompleteListener {

            if (it.isSuccessful){

                val intent = Intent(this, select_page::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)

                Toast.makeText(this,"signin sucessfull",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
            }
        }
    }

}
