package com.example.yeebum.screens.flows_control

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import kotlinx.android.synthetic.main.fragment_action_color_details.*


class ActionColorDetailsFragment : ActionsDetailsFragment() {

    private var flow: Flow? = null
    private val flowsViewModel: FlowsViewModel by viewModels {
        FlowsViewModelFactory((requireActivity().application as YeebumApplication).flowsRepository)
    }

    private lateinit var chooseFlowViewModel: ChooseFlowViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_action_color_details, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFragment()
    }

    //--------------------| get actual flow, setup all pickers and seekbars, add new color action |-----------------------
    private fun setupFragment() {
        chooseFlowViewModel = ViewModelProvider(requireActivity())[ChooseFlowViewModel::class.java]
        chooseFlowViewModel.getFlow().observe(viewLifecycleOwner) { flow = it }

        colorDetailsColorPicker.showOldCenterColor = false
        setupPickers(minuteColorPicker, secondsColorPicker, millisecondsColorPicker)
        setupBrightnessSeekBar(colorBrightnessSeekbar, brightnessColorPercent)

        addNewColorActionButton.setOnClickListener {
            if (flow != null) {
                addAction(
                    flow,
                    flowsViewModel,
                    ActionType.Color,
                    colorDetailsColorPicker.color,
                    colorBrightnessSeekbar.progress + 1,
                    getDuration(minuteColorPicker,secondsColorPicker,millisecondsColorPicker)
                )
                chooseFlowViewModel.setFlow(flow!!)
                findNavController().navigate(ActionColorDetailsFragmentDirections.actionActionDetailsFragmentToActionsFragment())
            }
        }

        actionDetailsBackButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
    //=====================================================================================================================


}