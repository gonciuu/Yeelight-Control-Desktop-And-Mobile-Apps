package com.example.yeebum.screens.adapters.recycler_views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yeebum.R
import com.example.yeebum.models.Action
import com.example.yeebum.models.ActionType
import com.example.yeebum.screens.adapters.recycler_views.view_holders.ActionsViewHolder

class ActionsRecyclerViewAdapter(private val actions: ArrayList<Action>) :
    RecyclerView.Adapter<ActionsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionsViewHolder {
        return ActionsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.action_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ActionsViewHolder, position: Int) {
        when (actions[holder.adapterPosition].type) {
            ActionType.Color -> setupColorAction(holder)
            ActionType.ColorTemp -> setupColorTempAction(holder)
            ActionType.Sleep -> setupPauseAction(holder)
        }


    }

    override fun getItemCount(): Int {
        return actions.size
    }


    private fun setupColorAction(holder: ActionsViewHolder) {
        //holder.thirdValueImage = actions[holder.adapterPosition].color
    }

    private fun setupColorTempAction(holder: ActionsViewHolder) {

    }

    private fun setupPauseAction(holder: ActionsViewHolder) {

    }
}