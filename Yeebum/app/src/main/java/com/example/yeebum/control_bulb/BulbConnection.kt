package com.example.yeebum.control_bulb

import android.content.Context
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.example.yeebum.screens.components.Helpers
import com.example.yeebum.screens.components.LoadingDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedOutputStream
import java.net.ConnectException
import java.net.Socket

class BulbConnection {

    //-------------------------------| Connect to the bulb |----------------------------------
    @Suppress("BlockingMethodInNonBlockingContext")
    fun connectToBulb(context: Context, activity:FragmentActivity,bulbControlViewModel:BulbControlViewModel,view:View) {
        val helpers = Helpers()
        val dialog = LoadingDialog.getDialog(context, "Connecting...")
        dialog.show()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val socket = Socket("192.168.0.108", 55443)
                socket.keepAlive = true
                activity.runOnUiThread {
                    bulbControlViewModel.setSocket(socket)
                    bulbControlViewModel.setBOS(BufferedOutputStream(socket.getOutputStream()))
                }
            } catch (connectEx: ConnectException) {
                helpers.showSnackBar(
                    view,
                    "Error! Check your internet connection.",
                    null,
                    null
                )
            } catch (ex: Exception) {
                helpers.showSnackBar(view, ex.message!!, null, null)
            }
            dialog.dismiss()
        }
    }
    //========================================================================================
}