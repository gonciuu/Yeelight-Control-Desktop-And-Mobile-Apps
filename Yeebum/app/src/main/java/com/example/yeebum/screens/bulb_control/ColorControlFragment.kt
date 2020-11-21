package com.example.yeebum.screens.bulb_control

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.yeebum.R
import com.example.yeebum.control_bulb.Constants.CMD_OFF
import com.example.yeebum.control_bulb.Constants.CMD_ON
import com.example.yeebum.control_bulb.Constants.ID
import com.example.yeebum.screens.components.Helpers
import com.example.yeebum.screens.components.LoadingDialog
import kotlinx.android.synthetic.main.fragment_color_control.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedOutputStream
import java.lang.Exception
import java.net.ConnectException
import java.net.Socket


class ColorControlFragment : Fragment() {

    private lateinit var socket: Socket
    private lateinit var mBos: BufferedOutputStream

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_color_control, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupColorPicker()
        connectToBulb()


        //-----------------| Turn On\Off Button |--------------------
        var isOn = true
        onOffButton.setOnClickListener {
            isOn = !isOn
            toggleSwitch(isOn)
        }
        //===========================================================


    }

    //-------------------------------| Connect to the bulb |----------------------------------
    @Suppress("BlockingMethodInNonBlockingContext")
    private fun connectToBulb() {
        val helpers = Helpers()
        val dialog = LoadingDialog.getDialog(requireContext(), "Connecting...")
        dialog.show()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                socket = Socket("192.168.0.108", 55443)
                socket.keepAlive = true
                mBos = BufferedOutputStream(socket.getOutputStream())
            } catch (connectEx: ConnectException) {
                helpers.showSnackBar(requireView(), "Error! Check your internet connection.", null, null)
            } catch (ex: Exception) {
                helpers.showSnackBar(requireView(), ex.message!!, null, null)
            }
            dialog.dismiss()
        }
    }
    //========================================================================================


    //-------------------------| Change bulb to on or Off state |-------------------------------------
    private fun toggleSwitch(isOn: Boolean) {
        onOffButton.setColorFilter(
            if (isOn) Color.argb(255, 30, 255, 30) else Color.argb(
                255,
                255,
                30,
                30
            )
        )
        write(if (isOn) CMD_ON.replace("%id", ID) else CMD_OFF.replace("%id", ID))
    }
    //================================================================================================


    private fun write(cmd: String) {
        if (socket.isConnected)
            executeAction(cmd)
        else
            Log.d("TAG", "mBos = null or mSocket is closed")
    }


    @Suppress("BlockingMethodInNonBlockingContext")
    private fun executeAction(cmd: String) {
        CoroutineScope(Dispatchers.IO).launch {
            mBos.write(cmd.toByteArray())
            mBos.flush()
        }
    }


    //--------------| Setup Wheel Color Picker |----------------
    private fun setupColorPicker() {
        colorPicker.showOldCenterColor = false
        colorPicker.setOnColorSelectedListener {
            Log.d("TAG", it.toString())
        }
    }
    //===========================================================

}
