package com.example.yeebum.screens.flows_control

import android.annotation.SuppressLint
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
import com.example.yeebum.control_bulb.ActionsListener
import com.example.yeebum.control_bulb.ChooseFlowViewModel
import com.example.yeebum.databases.flows_database.FlowsViewModel
import com.example.yeebum.databases.flows_database.FlowsViewModelFactory
import com.example.yeebum.models.Action
import com.example.yeebum.models.Flow
import com.example.yeebum.screens.adapters.recycler_views.ActionsRecyclerViewAdapter
import com.example.yeebum.screens.components.Helpers
import kotlinx.android.synthetic.main.fragment_actions.*

class ActionsFragment : Fragment(), ActionsListener {

    private var flow: Flow? = null

    private val helpers = Helpers()
    private val flowsViewModel: FlowsViewModel by viewModels {
        FlowsViewModelFactory((requireActivity().application as YeebumApplication).flowsRepository)
    }


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

    //------------------------------| Setup flow data in ui |-----------------------------------
    @SuppressLint("SetTextI18n")
    private fun setupFlow(){
        chooseFlowViewModel = ViewModelProvider(requireActivity())[ChooseFlowViewModel::class.java]
        chooseFlowViewModel.getFlow().observe(viewLifecycleOwner) { fl ->

            this.flow = fl
            if(flow!=null){
                flowTitle.text = flow!!.name
                var duration = 0
                flow!!.actions.forEach { duration+=it.duration/1000 }
                flowDurationText.text = "${duration}s"

                actionsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                actionsRecyclerView.adapter = ActionsRecyclerViewAdapter(flow!!.actions,this)


                addActionButton.setOnClickListener {
                    findNavController().navigate(ActionsFragmentDirections.actionActionsFragmentToAddActionFragment())
                }

                actionsBackButton.setOnClickListener {
                    requireActivity().onBackPressed()
                }
            }

        }
    }
    //===========================================================================================


    //edit action
    override fun onActionEdit(action: Action) {

    }

    //delete action
    override fun onActionDelete(action: Action) {
        val index =  flow!!.actions.indexOf(action)
        if(flow!=null){
            flow!!.actions.removeAt(index)
            setFlow(flow!!)
            helpers.showSnackBar(requireView(),"Deleted","Undo"){
                flow!!.actions.add(index,action)
                setFlow(flow!!)
            }
        }
    }

    //on long click action
    override fun onActionSelected(action: Action) {
        Helpers().getChooseOptionDialog(requireActivity(),requireContext(),"Choose option",this, action).show()
    }

    //update flow and setup it into viewmodel
    private fun setFlow(flow: Flow){
        flowsViewModel.insertFlow(flow)
        chooseFlowViewModel.setFlow(flow)
    }
}