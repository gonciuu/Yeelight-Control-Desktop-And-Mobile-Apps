package com.example.yeebum.screens.adapters.recycler_views

import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yeebum.R
import com.example.yeebum.screens.adapters.recycler_views.view_holders.DevicesViewHolder
import com.example.yeebum.screens.bulbs.SearchedBulbsInterface

class DevicesAdapter(private val listOfDevices:ArrayList<HashMap<String,String>>,private val listener:SearchedBulbsInterface,private val dialog: AlertDialog):RecyclerView.Adapter<DevicesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevicesViewHolder {
        return DevicesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.device_card,parent,false))
    }

    override fun onBindViewHolder(holder: DevicesViewHolder, position: Int) {
        val data = listOfDevices[holder.adapterPosition]
        Log.d("TAG",data.toString())
        holder.bulbName.text= "Type = " + data["model"]
        holder.bulbData.text= "location = " + data["Location"]
        holder.allDeviceCard.setOnClickListener {
            dialog.dismiss()
            listener.onChooseBulb(data)
        }

    }

    override fun getItemCount(): Int {
        return listOfDevices.size
    }

}