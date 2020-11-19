package com.example.yeebum.screens.flows_control

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.yeebum.R
import kotlinx.android.synthetic.main.fragment_add_action.*

class AddActionFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =  inflater.inflate(R.layout.fragment_add_action, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listOfOptions = arrayListOf<LinearLayout>(addBrightnessAction,addColorAction,addPauseAction,addColorTempAction)
        val listOfData = arrayListOf<String>("brightness","color","pause","color_temp")

        listOfOptions.forEach { layout ->
            layout.setOnClickListener {
                findNavController().navigate(AddActionFragmentDirections.actionAddActionFragmentToActionDetailsFragment().actionId,bundleOf("actionType" to listOfData[listOfOptions.indexOf(layout)]))
            }
        }

        addActionBackButton.setOnClickListener {
            requireActivity().onBackPressed()
        }


    }


}