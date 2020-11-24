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
import java.net.SocketException

class BulbConnection {

    //-------------------------------| Connect to the bulb |----------------------------------
    @Suppress("BlockingMethodInNonBlockingContext")
    fun connectToBulb(context: Context, activity:FragmentActivity,bulbControlViewModel:BulbControlViewModel,view:View,ip:String,port:Int) {
        val helpers = Helpers()
        val dialog = LoadingDialog.getDialog(context, "Connecting...")
        dialog.show()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val socket = Socket(ip,port)
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

    //---------------------------| Execute write command action |---------------------------
    @Suppress("BlockingMethodInNonBlockingContext")
    fun executeAction(cmd: String, mBos:BufferedOutputStream,activity: FragmentActivity, view: View,context: Context,bulbControlViewModel:BulbControlViewModel, ip:String,port:Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try{
                mBos.write(cmd.toByteArray())
                mBos.flush()
            }catch (socketEx: SocketException){
                //helpers.showSnackBar(requireView(), socketEx.message!!,null,null)
                activity.runOnUiThread {
                    connectToBulb(context,activity, bulbControlViewModel, view, ip, port)
                }
            }catch (ex:Exception){
                Helpers().showSnackBar(view, ex.message!!,null,null)
            }
        }
    }
    //======================================================================================
}