package com.example.yeebum.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.example.yeebum.R
import kotlinx.android.synthetic.main.fragment_action_details.*
import kotlinx.android.synthetic.main.fragment_action_details.colorPicker
import kotlinx.android.synthetic.main.fragment_color_control.*


class ActionDetailsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
            = inflater.inflate(R.layout.fragment_action_details, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        minutePicker.apply {
            minValue=0
            maxValue=60
            setOnLongPressUpdateInterval(100)
        }
        secondsPicker.apply {
            minValue=0
            maxValue=60
            setOnLongPressUpdateInterval(100)
        }
        millisecondsPicker.apply {
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
        colorPicker.showOldCenterColor = false

        brightnessSeekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                brightnessPercent.text = "${p1+1}%"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                //TODO("Not yet implemented")
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                //TODO("Not yet implemented")
            }

        })

    }

}