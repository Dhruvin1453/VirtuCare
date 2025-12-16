package com.example.virtucare
import com.bumptech.glide.Glide


import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter

class topdoctoradapter (private val doctorlist : ArrayList<doctordataModel>): RecyclerView.Adapter<topdoctoradapter.MyViewholder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.topdoctor_list,parent,false)

        return MyViewholder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {

        val currentList = doctorlist[position]

        holder.tvDname.text = currentList.name
        holder.tvExperience.text = currentList.experience
        holder.tvSpecialis.text = currentList.specilization

        Glide.with(holder.itemView.context)
            .load(currentList.imageurl)
            .into(holder.imageView)

    }

    override fun getItemCount(): Int {

        return doctorlist.size
    }


    class MyViewholder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val tvDname : TextView = itemView.findViewById<TextView>(R.id.tvDname)
        val tvSpecialis : TextView = itemView.findViewById<TextView>(R.id.tvSpecialis)
        val tvExperience : TextView = itemView.findViewById<TextView>(R.id.tvExperience)
        val imageView: ImageView = itemView.findViewById(R.id.doctorImage)

    }


}