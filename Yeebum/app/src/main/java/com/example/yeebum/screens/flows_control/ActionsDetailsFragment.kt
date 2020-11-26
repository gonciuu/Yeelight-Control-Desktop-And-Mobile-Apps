package com.example.yeebum.screens.flows_control

import android.annotation.SuppressLint
import android.widget.NumberPicker
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.yeebum.databases.flows_database.FlowsViewModel
import com.example.yeebum.models.Action
import com.example.yeebum.models.ActionType
import com.example.yeebum.models.Flow

abstract class ActionsDetailsFragment:Fragment() {

    //-------------| Setup time pickers |------------------
     fun setupPickers(picker1:NumberPicker,picker2:NumberPicker,picker3:NumberPicker,){
        picker1.apply {
            minValue=0
            maxValue=60
            setOnLongPressUpdateInterval(100)
        }
        picker2.apply {
            minValue=0
            maxValue=60
            setOnLongPressUpdateInterval(100)
        }
        picker3.apply {
            minValue = 1
            maxValue = 10
            val milliseconds = arrayOfNulls<String>(10)
            for (i in milliseconds.indices) {
                val number = (i * 100).toString()
                milliseconds[i] = if (number.length < 2) "0$number" else number
            }
            displayedValues = milliseconds
            setOnLongPressUpdateInterval(100)
        }

    }
    //===============================================================================

    //-----------------------|Setup brightness seekbar |--------------------------
    fun setupBrightnessSeekBar(seekBar:SeekBar, textView:TextView){
        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                textView.text = "${p1+1}%"
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {
                //TODO("Not yet implemented")
            }
            override fun onStopTrackingTouch(p0: SeekBar?) {
                //TODO("Not yet implemented")
            }
        })
    }

    //===============================================================================

    //-----------------------| Save new Action to database |-------------------------

    fun addAction(flow:Flow?, flowsViewModel: FlowsViewModel,actionType: ActionType,color:Int,brightness:Int,duration:Int){
        if (flow != null) {
            val action = Action(
                actionType,
                color,
                brightness,
                duration
            )
            flow.actions.add(action)
            flowsViewModel.insertFlow(flow)
        }

    }

    //===============================================================================

    //get duration in millis from number pickers
    fun getDuration(minutesPicker:NumberPicker,secondsPicker:NumberPicker,millisecondsPicker:NumberPicker):Int=
         minutesPicker.value * 60000 + secondsPicker.value * 1000 + (millisecondsPicker.value - 1) * 100


}