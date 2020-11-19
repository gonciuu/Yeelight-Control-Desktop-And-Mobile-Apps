package com.example.yeebum.screens

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import com.example.yeebum.R
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.fragment_welcome.*


class WelcomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_welcome, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        welcomeButton.setOnClickListener {
            findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToAllBulbsFragment())
        }
        xd.setOnClickListener {
            val drawer = requireActivity().findViewById<DrawerLayout>(R.id.drawer_layout)
            drawer.openDrawer(Gravity.START)
        }
    }


}