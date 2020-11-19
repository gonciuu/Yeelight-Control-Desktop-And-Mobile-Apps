package com.example.yeebum.screens.flows_control

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.yeebum.R
import com.example.yeebum.YeebumApplication
import com.example.yeebum.databases.flows_database.FlowsViewModel
import com.example.yeebum.databases.flows_database.FlowsViewModelFactory
import com.example.yeebum.models.Flow
import kotlinx.android.synthetic.main.fragment_action_details.*
import kotlinx.android.synthetic.main.fragment_action_details.colorPicker


class ActionDetailsFragment : Fragment() {


    lateinit var flow: Flow

    private val flowsViewModel:FlowsViewModel by viewModels{
        FlowsViewModelFactory((requireActivity().application as YeebumApplication).flowsRepository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_action_details, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val component = DaggerFlowComponent.create()
        //component.inject(this)

        setupPickers()
        setupSeekbar()

        addNewActionButton.setOnClickListener {
            findNavController().navigate(ActionDetailsFragmentDirections.actionActionDetailsFragmentToActionsFragment())
        }

        actionDetailsBackButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
        saveFlow()
    }

    //-------------| Setup time pickers |------------------
    private fun setupPickers(){
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
    }
    //===============================================================================

    //-----------------------|Setup brightness seekbar |--------------------------
    private fun setupSeekbar(){
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
    //===============================================================================

    private fun saveFlow(){
        addNewActionButton.setOnClickListener {
            flowsViewModel.insertFlow(flow)
        }
    }

}