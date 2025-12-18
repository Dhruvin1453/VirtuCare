package com.example.virtucare
import kotlinx.parcelize.Parcelize
import android.os.Parcelable


@Parcelize
data class doctordataModel(

    var name: String? = null,
    var specialization: String? = null,
    var qualification: String? = null,
    var experience: String? = null,
    var registrationNumber: String? = null,
    var hospitalName: String? = null,
    var location: String? = null,
    var consultationType: String? = null, // "Online", "Offline", "Both"
    var consultationFee: String? = null,
    var availableDaysTime: String? = null,
    var contactNumber: String? = null,
    var email: String? = null,
    var bio: String? = null,
    var imageUrl: String? = null,
    var status: String? = "Pending"

): Parcelable
