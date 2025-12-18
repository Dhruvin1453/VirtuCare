package com.example.virtucare

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.net.TetheringManager
import androidx.core.content.ContextCompat
import org.w3c.dom.Text


class appointmentsadapter(private val context: Context, private val appointminlist: ArrayList<appointmentDataModel>) : RecyclerView.Adapter<appointmentsadapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.appointmentlist, parent, false)

        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentlist = appointminlist[position]

        holder.tname.text = currentlist.name
        holder.tphone.text = currentlist.mobile
        holder.tdate.text = currentlist.date
        holder.ttime.text = currentlist.time
        holder.tdoctor.text = currentlist.doctorname
        holder.tstatus.text = currentlist.status

        when (currentlist.status){

            "Approved" -> {

                holder.tstatus.setTextColor(ContextCompat.getColor(   context,R.color.green )       )
            }


            "Disapproved" -> {

                holder.tstatus.setTextColor(ContextCompat.getColor(   context,R.color.red )       )
            }



        }



    }

    override fun getItemCount(): Int {
        return appointminlist.size
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tname: TextView = itemView.findViewById<TextView>(R.id.tname)
        val tphone: TextView = itemView.findViewById<TextView>(R.id.tphone)
        val tdate: TextView = itemView.findViewById<TextView>(R.id.tdate)
        val ttime: TextView = itemView.findViewById<TextView>(R.id.ttime)
        val tdoctor: TextView = itemView.findViewById<TextView>(R.id.tdoctor)
        val tstatus : TextView = itemView.findViewById<TextView>(R.id.tstatus)


    }

}