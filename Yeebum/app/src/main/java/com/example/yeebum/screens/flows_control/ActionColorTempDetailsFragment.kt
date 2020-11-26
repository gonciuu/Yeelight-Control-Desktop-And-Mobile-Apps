package com.example.yeebum.screens.flows_control

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.example.yeebum.R
import kotlinx.android.synthetic.main.fragment_action_color_temp_details.*


class ActionColorTempDetailsFragment : ActionsDetailsFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_action_color_temp_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPickers(minuteColorTempPicker,secondsColorTempPicker,millisecondsColorTempPicker)
        setupBrightnessSeekBar(colorTempBrightnessSeekbar,brightnessColorTempPercent)

        colorTempSeekBar.apply{
            max= 4800
            setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
                @SuppressLint("SetTextI18n")
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    CTValue.text = "${p1+1700}k"
                }
                override fun onStartTrackingTouch(p0: SeekBar?) {
                    //TODO("Not yet implemented")
                }
                override fun onStopTrackingTouch(p0: SeekBar?) {
                    //TODO("Not yet implemented")
                }

            })
            progress = 4000
        }
    }


}