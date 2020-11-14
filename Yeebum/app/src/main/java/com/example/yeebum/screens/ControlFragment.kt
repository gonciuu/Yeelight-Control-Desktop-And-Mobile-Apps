package com.example.yeebum.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.yeebum.R
import com.example.yeebum.screens.adapters.pager_views.ControlViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_control.*


class ControlFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_control, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controlViewPager.adapter = ControlViewPagerAdapter(requireActivity())
    }

}