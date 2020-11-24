package com.example.yeebum.screens.adapters.recycler_views

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yeebum.R
import com.example.yeebum.models.Bulb
import com.example.yeebum.screens.bulbs.BulbsInterface
import com.example.yeebum.screens.adapters.recycler_views.view_holders.AllBulbsHolder

class AllBulbsRecyclerViewAdapter(private val listOfBulbs: List<Bulb> ,private val listener: BulbsInterface): RecyclerView.Adapter<AllBulbsHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllBulbsHolder {
       return AllBulbsHolder(LayoutInflater.from(parent.context).inflate(R.layout.bulb_card,parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AllBulbsHolder, position: Int) {
        val bulb = listOfBulbs[holder.adapterPosition]
        holder.bulbName?.text = bulb.name
        holder.bulbIp?.text = "ip: ${bulb.ip}"
        holder.bulbPort?.text = "port: ${bulb.port}"

        holder.allBulbCard?.setOnClickListener {
            listener.onBulbClick(bulb.ip,bulb.port)
        }

    }

    override fun getItemCount(): Int {
        return listOfBulbs.size
    }
}