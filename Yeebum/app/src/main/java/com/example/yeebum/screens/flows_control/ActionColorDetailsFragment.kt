package com.example.yeebum.screens.flows_control

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.yeebum.R
import kotlinx.android.synthetic.main.fragment_action_color_details.*
import kotlinx.android.synthetic.main.fragment_action_color_details.actionDetailsBackButton
import kotlinx.android.synthetic.main.fragment_action_color_details.addNewActionButton
import kotlinx.android.synthetic.main.fragment_action_color_details.colorPicker



class ActionColorDetailsFragment : ActionsDetailsFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_action_color_details, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        colorPicker.showOldCenterColor = false
        setupPickers(minuteColorPicker,secondsColorPicker,millisecondsColorPicker)
        setupBrightnessSeekBar(colorBrightnessSeekbar,brightnessColorPercent)

        addNewActionButton.setOnClickListener {
            findNavController().navigate(ActionColorDetailsFragmentDirections.actionActionDetailsFragmentToActionsFragment())
        }

        actionDetailsBackButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }




}