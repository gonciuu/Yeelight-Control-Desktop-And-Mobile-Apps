package com.example.yeebum.screens.bulb_control

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yeebum.R
import com.example.yeebum.YeebumApplication
import com.example.yeebum.control_bulb.BulbConnection
import com.example.yeebum.control_bulb.BulbControlViewModel
import com.example.yeebum.control_bulb.Constants
import com.example.yeebum.control_bulb.Constants.getFlowCommand
import com.example.yeebum.databases.flows_database.FlowsViewModel
import com.example.yeebum.databases.flows_database.FlowsViewModelFactory
import com.example.yeebum.models.Flow
import com.example.yeebum.screens.flows_control.FlowsInterface
import com.example.yeebum.screens.adapters.recycler_views.FlowsRecyclerViewAdapter
import com.example.yeebum.screens.components.Helpers
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_flow_control.*
import java.io.BufferedOutputStream
import java.lang.Exception
import java.net.Socket


class FlowControlFragment : Fragment(), FlowsInterface {

    private lateinit var socket: Socket
    private lateinit var mBos: BufferedOutputStream
    private val helpers = Helpers()
    private lateinit var bulbControlViewModel: BulbControlViewModel
    private val bulbConnection = BulbConnection()

    private var ip:String? = null
    private var port:Int? = null


    private val flowsViewModel:FlowsViewModel by viewModels {
        FlowsViewModelFactory((requireActivity().application as YeebumApplication).flowsRepository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_flow_control, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bulbControlViewModel = ViewModelProvider(requireActivity())[BulbControlViewModel::class.java]

        getBulbSocketAndBOS()
        addFlowButton.setOnClickListener {
            findNavController().navigate(ControlFragmentDirections.actionControlFragmentToEnterActionNameFragment())
        }

        getAllFlows()
    }


    override fun onSelectFlow(flow:Flow) {
        findNavController().navigate(ControlFragmentDirections.actionControlFragmentToActionsFragment().actionId,
            bundleOf("flow" to Gson().toJson(flow)))
        //write(getFlowCommand(4, arrayListOf()).replace("%id", Constants.ID))
    }

    //--------------| Get all Flows From Database |----------------
    private fun getAllFlows(){
        flowsViewModel.allFlows.observe(viewLifecycleOwner){
            flowsRecyclerView.layoutManager = LinearLayoutManager(context)
            flowsRecyclerView.adapter = FlowsRecyclerViewAdapter(this,it)
        }
    }
    //===============================================================


    //-------------------------------| Get Bulb Socket, Buffer Stream, Ip, Port From Control Fragment |----------------------------------
    private fun getBulbSocketAndBOS() {
        bulbControlViewModel.getIp().observe(viewLifecycleOwner){ip = it}
        bulbControlViewModel.getPort().observe(viewLifecycleOwner){port = it}
        bulbControlViewModel.getSocket().observe(viewLifecycleOwner){socket = it}
        bulbControlViewModel.getBOS().observe(viewLifecycleOwner){mBos = it}
    }
    //=====================================================================================================================================

    //----------------------| Write Command |--------------------------
    private fun write(cmd: String) {
        try{
            if (::socket.isInitialized && socket.isConnected && ip!=null && port!=null)
                bulbConnection.executeAction(cmd,mBos,requireActivity(),requireView(),requireContext(),bulbControlViewModel,ip!!,port!!)
            else
                helpers.showSnackBar(requireView(), "Check your bulb ip and port", null, null)
        }catch (ex: Exception){
            helpers.showSnackBar(requireView(),ex.message!!,null,null)
        }
    }
    //=================================================================
}