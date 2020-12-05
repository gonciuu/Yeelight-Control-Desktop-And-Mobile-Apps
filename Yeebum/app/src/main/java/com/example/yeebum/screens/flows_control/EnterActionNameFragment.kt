package com.example.yeebum.screens.flows_control

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.yeebum.R
import com.example.yeebum.YeebumApplication
import com.example.yeebum.databases.flows_database.FlowsViewModel
import com.example.yeebum.databases.flows_database.FlowsViewModelFactory
import com.example.yeebum.hilt.DaggerFlowComponent
import com.example.yeebum.models.Flow
import com.example.yeebum.screens.components.Helpers
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_enter_action_name.*
import javax.inject.Inject

@AndroidEntryPoint
class EnterActionNameFragment : Fragment() {


    @Inject
    lateinit var flow: Flow

    private val flowsViewModel: FlowsViewModel by viewModels{
        FlowsViewModelFactory((requireActivity().application as YeebumApplication).flowsRepository)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_enter_action_name, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val component = DaggerFlowComponent.create()
        component.inject(this)

        enterNameBackButton.setOnClickListener {
            requireActivity().onBackPressed()
        }


        nameNextButton.setOnClickListener {
            saveFlow()
        }
        val helpers = Helpers()
        helpers.onSubmitKeyboard(flowNameInput) {
            helpers.closeKeyboard(requireActivity())
            saveFlow()
        }

    }

    private fun saveFlow(){
            flow.name = flowNameInput.text.toString()
            flowsViewModel.insertFlow(flow)
            requireActivity().onBackPressed()
    }
}