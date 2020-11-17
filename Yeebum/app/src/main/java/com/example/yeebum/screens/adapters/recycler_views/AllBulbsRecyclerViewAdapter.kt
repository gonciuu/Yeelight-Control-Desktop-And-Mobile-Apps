package com.example.yeebum.screens.adapters.recycler_views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yeebum.R
import com.example.yeebum.models.Bulb
import com.example.yeebum.screens.BulbsInterface
import com.example.yeebum.screens.adapters.recycler_views.view_holders.AllBulbsHolder

class AllBulbsRecyclerViewAdapter(private val listOfBulbs: List<Bulb> ,private val listener:BulbsInterface): RecyclerView.Adapter<AllBulbsHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllBulbsHolder {
       return AllBulbsHolder(LayoutInflater.from(parent.context).inflate(R.layout.bulb_card,parent,false))
    }

    override fun onBindViewHolder(holder: AllBulbsHolder, position: Int) {
        val bulb = listOfBulbs[holder.adapterPosition]
        holder.bulbName?.text = bulb.name
        holder.bulbIp?.text = bulb.ip
        holder.bulbPort?.text = bulb.port.toString()

        holder.allBulbCard?.setOnClickListener {
            listener.onBulbClick()
        }

    }

    override fun getItemCount(): Int {
        return listOfBulbs.size
    }
}