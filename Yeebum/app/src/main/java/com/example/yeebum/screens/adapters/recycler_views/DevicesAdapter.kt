package com.example.yeebum.screens.adapters.recycler_views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yeebum.R
import com.example.yeebum.screens.adapters.recycler_views.view_holders.DevicesViewHolder

class DevicesAdapter(private val listOfDevices:ArrayList<HashMap<String,String>>):RecyclerView.Adapter<DevicesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevicesViewHolder {
        return DevicesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.device_card,parent,false))
    }

    override fun onBindViewHolder(holder: DevicesViewHolder, position: Int) {
        val data = listOfDevices[holder.adapterPosition]
        holder.bulbName.text= "Type = " + data["model"]
        holder.bulbData.text= "location = " + data["Location"]
    }

    override fun getItemCount(): Int {
        return listOfDevices.size
    }

}