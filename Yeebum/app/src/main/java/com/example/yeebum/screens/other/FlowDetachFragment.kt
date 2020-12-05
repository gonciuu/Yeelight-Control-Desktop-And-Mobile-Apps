package com.example.yeebum.screens.other

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yeebum.R
import com.example.yeebum.YeebumApplication
import com.example.yeebum.control_bulb.ChooseFlowViewModel
import com.example.yeebum.databases.flows_database.FlowsViewModel
import com.example.yeebum.databases.flows_database.FlowsViewModelFactory
import com.example.yeebum.models.Flow
import com.example.yeebum.screens.adapters.recycler_views.FlowsRecyclerViewAdapter
import com.example.yeebum.screens.bulb_control.ControlFragmentDirections
import com.example.yeebum.screens.components.Helpers
import com.example.yeebum.screens.flows_control.FlowsInterface
import kotlinx.android.synthetic.main.fragment_flow_detach.*


class FlowDetachFragment : Fragment(),FlowsInterface {
    private lateinit var chooseFlowViewModel: ChooseFlowViewModel
    private val flowsViewModel: FlowsViewModel by viewModels {
        FlowsViewModelFactory((requireActivity().application as YeebumApplication).flowsRepository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_flow_detach, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chooseFlowViewModel = ViewModelProvider(requireActivity())[ChooseFlowViewModel::class.java]
        getAllFlows()

        flowDetachBackButton.setOnClickListener{requireActivity().onBackPressed()}
        addDetachFlowButton.setOnClickListener { findNavController().navigate(FlowDetachFragmentDirections.actionFlowDetachFragmentToEnterActionNameFragment()) }
    }


    //--------------| Get all Flows From Database |----------------
    private fun getAllFlows(){
        flowsViewModel.allFlows.observe(viewLifecycleOwner){
            if(it.isNullOrEmpty()) emptyFlowDetachListText.visibility = View.VISIBLE
            else emptyFlowDetachListText.visibility = View.GONE

            detachedFlowsRecyclerView.layoutManager = LinearLayoutManager(context)
            detachedFlowsRecyclerView.adapter = FlowsRecyclerViewAdapter(this,it)
        }
    }
    //===============================================================


    override fun onEditFlow(flow: Flow) {
        chooseFlowViewModel.setFlow(flow)
        findNavController().navigate(FlowDetachFragmentDirections.actionFlowDetachFragmentToActionsFragment())
    }

    override fun onStartFlow(flow: Flow) {}

    override fun onDeleteFlow(flow: Flow) {
        flowsViewModel.deleteFlow(flow)
        Helpers().showSnackBar(requireView(), "Deleted", "Undo") { flowsViewModel.insertFlow(flow) }
    }

}