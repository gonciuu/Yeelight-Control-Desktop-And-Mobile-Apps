package com.example.yeebum.screens.flows_control

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.yeebum.R
import kotlinx.android.synthetic.main.fragment_action_pause_details.*


class ActionPauseDetailsFragment : ActionsDetailsFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_action_pause_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPickers(minutePausePicker,secondsPausePicker,millisecondsPausePicker)
    }


}