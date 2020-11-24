package com.example.yeebum.screens.bulb_control

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yeebum.R
import com.example.yeebum.YeebumApplication
import com.example.yeebum.databases.flows_database.FlowsViewModel
import com.example.yeebum.databases.flows_database.FlowsViewModelFactory
import com.example.yeebum.screens.flows_control.FlowsInterface
import com.example.yeebum.screens.adapters.recycler_views.FlowsRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_flow_control.*


class FlowControlFragment : Fragment(), FlowsInterface {

    private val flowsViewModel:FlowsViewModel by viewModels {
        FlowsViewModelFactory((requireActivity().application as YeebumApplication).flowsRepository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_flow_control, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addFlowButton.setOnClickListener {
            findNavController().navigate(ControlFragmentDirections.actionControlFragmentToEnterActionNameFragment())
        }

        getAllFlows()
    }


    override fun onSelectAction() {
        findNavController().navigate(ControlFragmentDirections.actionControlFragmentToActionsFragment())
    }

    //--------------| Get all Flows From Database |----------------
    private fun getAllFlows(){
        flowsViewModel.allFlows.observe(viewLifecycleOwner){
            flowsRecyclerView.layoutManager = LinearLayoutManager(context)
            flowsRecyclerView.adapter = FlowsRecyclerViewAdapter(this,it)
        }
    }
    //===============================================================

}