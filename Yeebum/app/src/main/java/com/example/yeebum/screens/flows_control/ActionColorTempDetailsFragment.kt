package com.example.yeebum.screens.flows_control

import android.annotation.SuppressLint
import android.os.Bundle
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
import com.example.yeebum.models.Action
import com.example.yeebum.models.ActionType
import com.example.yeebum.models.Flow
import com.google.gson.Gson
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


        setupSeekBars()
        setupFragment()

        actionDetailsBackButton.setOnClickListener {
            requireActivity().onBackPressed()
        }

        if(!requireArguments().isEmpty){
            setupStartData()
        }

    }

    //--------------------------------| Setup SeekBars |------------------------------------
    private fun setupSeekBars(){
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
    }
    //=======================================================================================

    //------------------------------| Get Flow And insert Color temp Action into it |--------------------------------
    private fun setupFragment(){
        chooseFlowViewModel = ViewModelProvider(requireActivity())[ChooseFlowViewModel::class.java]
        chooseFlowViewModel.getFlow().observe(viewLifecycleOwner) { flow = it }

        addNewColorTempActionButton.setOnClickListener {
            if (flow != null) {
                if(requireArguments().isEmpty){
                   addNewAction()
                }else{
                    updateAction()
                }
                chooseFlowViewModel.setFlow(flow!!)
                findNavController().navigate(ActionColorTempDetailsFragmentDirections.actionActionColorTempDetailsFragmentToActionsFragment())
            }
        }
    }
    //=================================================================================================================

    private fun addNewAction(){
        addAction(
            flow,
            flowsViewModel,
            ActionType.ColorTemp,
            colorTempSeekBar.progress+1700,
            colorTempBrightnessSeekbar.progress + 1,
            getDuration(minuteColorTempPicker,secondsColorTempPicker,millisecondsColorTempPicker)
        )
    }

    private fun updateAction(){
        updateAction(
            flow,
            Gson().fromJson(requireArguments().getString("action"), Action::class.java),
            flowsViewModel,
            colorTempSeekBar.progress + 1700,
            colorTempBrightnessSeekbar.progress + 1,
            getDuration(minuteColorTempPicker,secondsColorTempPicker,millisecondsColorTempPicker)
        )
    }

    private fun setupStartData(){
        val action = Gson().fromJson(requireArguments().getString("action"), Action::class.java)
        colorTempBrightnessSeekbar.progress = action.brightness - 1
        colorTempSeekBar.progress = action.color - 1700
        setNumberPickersValue(minuteColorTempPicker,secondsColorTempPicker,millisecondsColorTempPicker,action.duration.toLong())
    }

}