package com.example.yeebum.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yeebum.R
import com.example.yeebum.screens.adapters.recycler_views.ActionsRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_actions.*

class ActionsFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):View? = inflater.inflate(R.layout.fragment_actions, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        actionsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        actionsRecyclerView.adapter = ActionsRecyclerViewAdapter()

        addActionButton.setOnClickListener {
            findNavController().navigate(ActionsFragmentDirections.actionActionsFragmentToAddActionFragment())
        }
    }

}