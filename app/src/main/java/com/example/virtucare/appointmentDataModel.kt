package com.example.virtucare

data class appointmentDataModel(

    val name : String ? = null,
    val email : String ? = null,
    val doctorname : String? = null,
    val mobile : String ? = null,
    val date : String ? = null,
    val time : String ? = null,

    val status: String? = "Pending"

)
