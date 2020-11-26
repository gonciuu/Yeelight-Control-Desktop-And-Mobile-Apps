package com.example.yeebum.screens.flows_control

import android.os.Bundle
import androidx.fragment.app.Fragment
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
import kotlinx.android.synthetic.main.fragment_action_pause_details.*
import kotlinx.android.synthetic.main.fragment_action_pause_details.actionDetailsBackButton


class ActionPauseDetailsFragment : ActionsDetailsFragment() {
    private var flow: Flow? = null
    private lateinit var chooseFlowViewModel: ChooseFlowViewModel
    private val flowsViewModel: FlowsViewModel by viewModels {
        FlowsViewModelFactory((requireActivity().application as YeebumApplication).flowsRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_action_pause_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chooseFlowViewModel = ViewModelProvider(requireActivity())[ChooseFlowViewModel::class.java]
        chooseFlowViewModel.getFlow().observe(viewLifecycleOwner) { flow = it }

        setupPickers(minutePausePicker, secondsPausePicker, millisecondsPausePicker)

        addNewPauseActionButton.setOnClickListener {
            if (flow != null) {
                addAction(
                    flow,
                    flowsViewModel,
                    ActionType.Sleep,
                    -1,
                    -1,
                    getDuration(minutePausePicker, secondsPausePicker, millisecondsPausePicker)
                )
                chooseFlowViewModel.setFlow(flow!!)
                findNavController().navigate(ActionPauseDetailsFragmentDirections.actionActionPauseDetailsFragmentToActionsFragment())
            }
        }

        actionDetailsBackButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }


}