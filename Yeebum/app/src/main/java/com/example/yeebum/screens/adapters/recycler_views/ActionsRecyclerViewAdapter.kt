package com.example.yeebum.screens.adapters.recycler_views

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.yeebum.R
import com.example.yeebum.control_bulb.ActionsListener
import com.example.yeebum.models.Action
import com.example.yeebum.models.ActionType
import com.example.yeebum.screens.adapters.recycler_views.view_holders.ActionsViewHolder
import com.example.yeebum.screens.components.Helpers


class ActionsRecyclerViewAdapter(private val actions: ArrayList<Action>, private val listener:ActionsListener) :
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
            holder.allCard.setOnLongClickListener {
                listener.onActionSelected(actions[holder.adapterPosition])
                true
            }
    }

    override fun getItemCount(): Int {
        return actions.size
    }


    @SuppressLint("SetTextI18n")
    private fun setupColorAction(holder: ActionsViewHolder) {
        holder.firstValue.text = "${actions[holder.adapterPosition].brightness}%"
        holder.secondValue.text = "${actions[holder.adapterPosition].duration/1000}s"
        ViewCompat.setBackgroundTintList(
            holder.thirdValueImage,
            ColorStateList.valueOf(actions[holder.adapterPosition].color)
        )
    }

    @SuppressLint("SetTextI18n")
    private fun setupColorTempAction(holder: ActionsViewHolder) {
        holder.firstValue.text = "${actions[holder.adapterPosition].brightness}%"
        holder.secondValue.text = "${actions[holder.adapterPosition].duration/1000}s"
        holder.thirdValueImage.visibility = View.INVISIBLE
        holder.thirdValueText.visibility = View.VISIBLE
        holder.thirdValueName.text = "Color Temp"
        holder.thirdValueText.text = "${actions[holder.adapterPosition].color}k"
    }

    @SuppressLint("SetTextI18n")
    private fun setupPauseAction(holder: ActionsViewHolder) {
        holder.firstValue.text = "${actions[holder.adapterPosition].duration/1000}s"
        holder.firstValueName.text = "Duration"
        holder.thirdValueName.visibility = View.GONE
        holder.thirdValueImage.visibility = View.GONE
        holder.secondValue.visibility = View.GONE
        holder.secondValueName.visibility = View.GONE
        holder.firstValue.setPadding(15,0,0,0)
        holder.firstValueName.setPadding(15,0,0,0)
    }
}