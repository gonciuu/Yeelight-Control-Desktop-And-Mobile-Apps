package com.example.yeebum.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yeebum.R
import com.example.yeebum.screens.adapters.recycler_views.AllBulbsRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_all_bulbs.*


class AllBulbsFragment : Fragment(), BulbsInterface {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_all_bulbs, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        bulbsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = AllBulbsRecyclerViewAdapter(this@AllBulbsFragment)
        }
        addBulbButton.setOnClickListener {
            findNavController().navigate(AllBulbsFragmentDirections.actionAllBulbsFragmentToEnterBulbDataFragment())
        }


    }

    override fun onBulbClick() {
        findNavController().navigate(AllBulbsFragmentDirections.actionAllBulbsFragmentToControlFragment())
    }


}