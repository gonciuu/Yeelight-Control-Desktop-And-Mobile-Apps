package com.example.yeebum.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.yeebum.R
import kotlinx.android.synthetic.main.fragment_enter_action_name.*


class EnterActionNameFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_enter_action_name, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameNextButton.setOnClickListener {
            findNavController().navigate(EnterActionNameFragmentDirections.actionEnterActionNameFragmentToActionsFragment())
        }
    }
}