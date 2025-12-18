package com.example.virtucare

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DoAppointAdapter(private val context: Context,private val appointmentslist : ArrayList<appointmentDataModel>) : RecyclerView.Adapter<DoAppointAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.appointindoctor,parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentList  = appointmentslist[position]
        holder.tname.text = currentList.name
        holder.tdate.text = currentList.date
        holder.ttime.text = currentList.time
        holder.tphone.text = currentList.mobile
        holder.tstatus.text = currentList.status

        val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().reference.child("Appointments").child("testid02")



        holder.btn_approve.setOnClickListener {

            databaseReference.child("status").setValue("Approved").addOnSuccessListener {

                holder.tstatus.text = "Approved"

            }

        }

        holder.btn_disapprove.setOnClickListener {

            databaseReference.child("status").setValue("Disapproved").addOnSuccessListener {

                holder.tstatus.text = "Disapproved"

            }

        }


        when (currentList.status){

            "Approved" -> {

                holder.tstatus.setTextColor(ContextCompat.getColor(   context,R.color.green )       )
            }


            "Disapproved" -> {

                holder.tstatus.setTextColor(ContextCompat.getColor(   context,R.color.red )       )
            }



        }

    }

    override fun getItemCount(): Int {

        return appointmentslist.size
    }


    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val tname = itemView.findViewById<TextView>(R.id.tname)
        val tdate = itemView.findViewById<TextView>(R.id.tdate)
        val ttime = itemView.findViewById<TextView>(R.id.ttime)
        val tphone = itemView.findViewById<TextView>(R.id.tphone)
        val tstatus = itemView.findViewById<TextView>(R.id.tstatus)
        val btn_approve = itemView.findViewById<TextView>(R.id.btn_approve)
        val btn_disapprove = itemView.findViewById<TextView>(R.id.btn_disapprove)


    }
}