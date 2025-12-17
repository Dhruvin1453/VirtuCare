package com.example.virtucare
import kotlinx.parcelize.Parcelize
import android.os.Parcelable


@Parcelize
data class doctordataModel(

    var name : String ? = null,
    var specilization : String ? = null,
    var experience : String ? = null,
    var bio : String ? = null,
    var mobile : String ? = null,
    var email : String ? = null,
    var imageurl : String ? = null

): Parcelable
