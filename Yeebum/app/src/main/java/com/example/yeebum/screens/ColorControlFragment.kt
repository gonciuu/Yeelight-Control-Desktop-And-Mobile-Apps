package com.example.yeebum.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.yeebum.R
import kotlinx.android.synthetic.main.fragment_color_control.*


class ColorControlFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_color_control, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupColorPicker()




    }

    private fun setupColorPicker(){
        colorPicker.showOldCenterColor = false

        colorPicker.setOnColorSelectedListener {
            Log.d("TAG",it.toString())
        }
    }
}