package com.example.virtucare
import com.bumptech.glide.Glide

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.virtucare.topdoctoradapter.MyViewholder

class doctorAdapter(private val doctorlist : ArrayList<doctordataModel>) : RecyclerView.Adapter <doctorAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView =  LayoutInflater.from(parent.context).inflate(R.layout.doctor_list,parent,false)

        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentList = doctorlist[position]

        holder.tvdoctor.text = currentList.name
        holder.tvspecial.text = currentList.specilization
        holder.tvExperience.text = currentList.experience

        Glide.with(holder.itemView.context)
            .load(currentList.imageurl)
            .into(holder.imageView)

    }

    override fun getItemCount(): Int {

        return doctorlist.size
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val tvdoctor : TextView = itemView.findViewById<TextView>(R.id.tvdoctor)
        val tvspecial : TextView = itemView.findViewById<TextView>(R.id.tvspecial)
        val tvExperience : TextView = itemView.findViewById<TextView>(R.id.tvExperience)
        val imageView: ImageView = itemView.findViewById(R.id.doctorImage)
    }
}