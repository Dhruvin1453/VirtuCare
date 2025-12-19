package com.example.virtucare
import android.content.Context
import android.content.Intent
import com.bumptech.glide.Glide
import android.app.Activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.virtucare.topdoctoradapter.MyViewholder

class doctorAdapter( private val context: Context,private val doctorlist : ArrayList<doctordataModel>) : RecyclerView.Adapter <doctorAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView =  LayoutInflater.from(parent.context).inflate(R.layout.doctor_list,parent,false)

        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentList = doctorlist[position]

        holder.tvdoctor.text = currentList.name
        holder.tvspecial.text = currentList.specialization
        holder.tvExperience.text = currentList.experience
        holder.tvTime.text = currentList.availableDaysTime

        Glide.with(holder.itemView.context)
            .load(currentList.imageUrl)
            .into(holder.imageView)



        holder.btnappointments.setOnClickListener {

            val intent = Intent(context, detailactivity::class.java)
            intent.putExtra("object",currentList)
            context.startActivity(intent)

            if( context is Activity) {
                context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }

    }

    override fun getItemCount(): Int {

        return doctorlist.size
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val tvdoctor : TextView = itemView.findViewById<TextView>(R.id.tvdoctor)
        val tvspecial : TextView = itemView.findViewById<TextView>(R.id.tvspecial)
        val tvExperience : TextView = itemView.findViewById<TextView>(R.id.tvExperience)
        val imageView: ImageView = itemView.findViewById(R.id.doctorImage)
        val btnappointments : Button = itemView.findViewById<Button>(R.id.btnappointments)
         val tvTime : TextView = itemView.findViewById<TextView>(R.id.tvTime)
    }
}