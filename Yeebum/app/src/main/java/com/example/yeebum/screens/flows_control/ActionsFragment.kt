package com.example.yeebum.screens.flows_control

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.solver.widgets.Helper
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yeebum.R
import com.example.yeebum.YeebumApplication
import com.example.yeebum.control_bulb.ChooseFlowViewModel
import com.example.yeebum.databases.flows_database.FlowsViewModel
import com.example.yeebum.databases.flows_database.FlowsViewModelFactory
import com.example.yeebum.models.Action
import com.example.yeebum.models.ActionType
import com.example.yeebum.models.Flow
import com.example.yeebum.screens.adapters.recycler_views.ActionsRecyclerViewAdapter
import com.example.yeebum.screens.components.Helpers
import com.google.gson.Gson
import kotlinx.android.synthetic.main.flow_card.*
import kotlinx.android.synthetic.main.fragment_actions.*

class ActionsFragment : Fragment() {


    private lateinit var chooseFlowViewModel: ChooseFlowViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_actions, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFlow()
    }

    @SuppressLint("SetTextI18n")
    private fun setupFlow(){
        chooseFlowViewModel = ViewModelProvider(requireActivity())[ChooseFlowViewModel::class.java]
        chooseFlowViewModel.getFlow().observe(viewLifecycleOwner) { flow ->

            flowTitle.text = flow.name
            var duration = 0
            flow.actions.forEach {
                duration+=it.duration/1000
            }
            flowDurationText.text = "${duration}s"

            actionsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            actionsRecyclerView.adapter = ActionsRecyclerViewAdapter(flow.actions)

            addActionButton.setOnClickListener {
                findNavController().navigate(ActionsFragmentDirections.actionActionsFragmentToAddActionFragment())
            }

            actionsBackButton.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

}