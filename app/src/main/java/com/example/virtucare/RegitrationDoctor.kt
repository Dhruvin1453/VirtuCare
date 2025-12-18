package com.example.virtucare

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.example.virtucare.databinding.ActivityRegitrationDoctorBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class RegitrationDoctor : AppCompatActivity() {

    private lateinit var binding: ActivityRegitrationDoctorBinding
    private var selectedImageUri: Uri? = null
    private var cameraUri: Uri? = null

    // 📸 Gallery picker
    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                selectedImageUri = it
                Glide.with(this).load(it).circleCrop().into(binding.imgDoctorPhoto)
            }
        }

    // 📷 Camera picker
    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success && cameraUri != null) {
                selectedImageUri = cameraUri
                Glide.with(this).load(cameraUri).circleCrop().into(binding.imgDoctorPhoto)
            }
        }

    // Camera permission
    private val cameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) openCamera()
            else Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityRegitrationDoctorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinners()

        binding.imgAddPhoto.setOnClickListener { showImagePickerOptions() }
        binding.imgDoctorPhoto.setOnClickListener { showImagePickerOptions() }

        binding.btnSubmitDoctor.setOnClickListener {
            uploadImageAndSaveDoctor()
        }
    }

    // 🔘 Image picker dialog
    private fun showImagePickerOptions() {
        val options = arrayOf("Select from Gallery", "Take Photo")
        AlertDialog.Builder(this)
            .setTitle("Choose Photo")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> galleryLauncher.launch("image/*")
                    1 -> {
                        if (checkSelfPermission(Manifest.permission.CAMERA) ==
                            android.content.pm.PackageManager.PERMISSION_GRANTED
                        ) {
                            openCamera()
                        } else {
                            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    }
                }
            }
            .show()
    }

    private fun openCamera() {
        val file = File(cacheDir, "doctor_${System.currentTimeMillis()}.jpg")
        cameraUri = FileProvider.getUriForFile(
            this,
            "${packageName}.provider",
            file
        )
        cameraLauncher.launch(cameraUri!!)
    }

    // ⬆ Upload image then save data
    private fun uploadImageAndSaveDoctor() {
        if (selectedImageUri == null) {
            Toast.makeText(this, "Please select doctor photo", Toast.LENGTH_SHORT).show()
            return
        }

        val storageRef = FirebaseStorage.getInstance()
            .getReference("DoctorImages")
            .child(System.currentTimeMillis().toString() + ".jpg")

        storageRef.putFile(selectedImageUri!!)
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    saveDoctorData(uri.toString())
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Image upload failed", Toast.LENGTH_SHORT).show()
            }
    }

    // 💾 Save doctor details
    private fun saveDoctorData(imageUrl: String) {
        val consultationType = when {
            binding.rbOnline.isChecked -> "Online"
            binding.rbOffline.isChecked -> "Offline"
            binding.rbBoth.isChecked -> "Both"
            else -> ""
        }

        val doctorId = FirebaseDatabase.getInstance()
            .getReference("Doctors")
            .push().key!!

        val doctor = doctordataModel(
            name = binding.etFullName.text.toString(),
            specialization = binding.spinnerSpecialization.selectedItem.toString(),
            qualification = binding.etQualification.text.toString(),
            experience = binding.etExperience.text.toString(),
            registrationNumber = binding.etRegistrationNumber.text.toString(),
            hospitalName = binding.etHospitalName.text.toString(),
            location = binding.etLocation.text.toString(),
            consultationType = consultationType,
            consultationFee = binding.etConsultationFee.text.toString(),
            availableDaysTime = binding.spinnerDateTime.selectedItem.toString(),
            contactNumber = binding.etContactNumber.text.toString(),
            email = binding.etEmail.text.toString(),
            bio = binding.etBio.text.toString(),
            imageUrl = imageUrl,
            status = "Pending"
        )

        FirebaseDatabase.getInstance()
            .getReference("Doctors")
            .child("testid07")
            .setValue(doctor)
            .addOnSuccessListener {
                Toast.makeText(this, "Doctor Registered Successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
    }

    // 🔽 Setup spinners
    private fun setupSpinners() {
        val specializationList = listOf(
            "Cardiologist", "Dermatologist", "Neurologist", "Pediatrician"
        )
        binding.spinnerSpecialization.adapter = ArrayAdapter(
            this,
            R.layout.spinner_item,
            specializationList
        ).apply {
            setDropDownViewResource(R.layout.spinner_dropdown_item)
        }

        val dateTimeList = listOf(
            "Mon – Sun (09:00 – 10:00)",
            "Mon – Fri (08:00 – 10:00)",
            "Sat – Sun (10:00 – 01:00)",
            "Mon – Thu (06:00 – 08:00)"
        )
        binding.spinnerDateTime.adapter = ArrayAdapter(
            this,
            R.layout.spinner_item,
            dateTimeList
        ).apply {
            setDropDownViewResource(R.layout.spinner_dropdown_item)
        }
    }
}
