package com.example.yeebum.screens.bulb_control

import android.graphics.Color
import android.graphics.ColorFilter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.yeebum.R
import com.example.yeebum.screens.components.LoadingDialog
import kotlinx.android.synthetic.main.fragment_color_control.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedOutputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.Socket


class ColorControlFragment : Fragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_color_control, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupColorPicker()
        connectToBulb()

        onOffButton.setOnClickListener {
            toggleSwitch(true)

        }

    }


    //--------------| Setup Wheel Color Picker |----------------
    private fun setupColorPicker(){
        colorPicker.showOldCenterColor = false

        colorPicker.setOnColorSelectedListener {
            Log.d("TAG",it.toString())
        }
    }
    //===========================================================


    private fun toggleSwitch(isOn:Boolean){
        onOffButton.setColorFilter(if(isOn) Color.argb(255,30,255,30) else Color.argb(255,30,30,255))
    }


    private fun connectToBulb(){
        val dialog = LoadingDialog.getDialog(requireContext(),"Connecting...")
        dialog.show()
        @Suppress("BlockingMethodInNonBlockingContext")
        CoroutineScope(Dispatchers.IO).launch {
            val socket = Socket("192.168.0.108",55443)
            socket.keepAlive = true
            val mBos = BufferedOutputStream(socket.getOutputStream())
            val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
            dialog.dismiss()
        }





    }

}
