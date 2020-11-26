package com.example.yeebum.screens.flows_control

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.yeebum.R
import com.example.yeebum.YeebumApplication
import com.example.yeebum.control_bulb.ChooseFlowViewModel
import com.example.yeebum.databases.flows_database.FlowsViewModel
import com.example.yeebum.databases.flows_database.FlowsViewModelFactory
import com.example.yeebum.models.ActionType
import com.example.yeebum.models.Flow
import kotlinx.android.synthetic.main.fragment_action_color_temp_details.*
import kotlinx.android.synthetic.main.fragment_action_color_temp_details.actionDetailsBackButton


class ActionColorTempDetailsFragment : ActionsDetailsFragment() {

    private var flow: Flow? = null
    private val flowsViewModel: FlowsViewModel by viewModels {
        FlowsViewModelFactory((requireActivity().application as YeebumApplication).flowsRepository)
    }

    private lateinit var chooseFlowViewModel: ChooseFlowViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_action_color_temp_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chooseFlowViewModel = ViewModelProvider(requireActivity())[ChooseFlowViewModel::class.java]
        chooseFlowViewModel.getFlow().observe(viewLifecycleOwner) { flow = it }

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
            progress = 2300
        }

        addNewColorTempActionButton.setOnClickListener {
            if (flow != null) {
                addAction(
                    flow,
                    flowsViewModel,
                    ActionType.ColorTemp,
                    colorTempSeekBar.progress+1700,
                    colorTempBrightnessSeekbar.progress + 1,
                    getDuration(minuteColorTempPicker,secondsColorTempPicker,millisecondsColorTempPicker)
                )
                chooseFlowViewModel.setFlow(flow!!)
                findNavController().navigate(ActionColorTempDetailsFragmentDirections.actionActionColorTempDetailsFragmentToActionsFragment())
            }
        }

        actionDetailsBackButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }


}