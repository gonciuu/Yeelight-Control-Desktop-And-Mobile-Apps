package com.example.yeebum.screens.bulbs

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.net.wifi.WifiManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.yeebum.R
import com.example.yeebum.YeebumApplication
import com.example.yeebum.databases.bulbs_database.*
import com.example.yeebum.models.Bulb
import com.example.yeebum.screens.components.Helpers
import com.example.yeebum.screens.components.LoadingDialog
import kotlinx.android.synthetic.main.fragment_enter_bulb_data.*
import kotlinx.coroutines.delay
import java.lang.Exception
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress


class EnterBulbDataFragment : Fragment() {

    companion object{
        const val MSG_SHOWLOG = 0
        const val MSG_FOUND_DEVICE = 1
        const val MSG_DISCOVER_FINISH = 2
        const val MSG_STOP_SEARCH = 3
        const val UDP_HOST = "239.255.255.250"
        const val UDP_PORT = 1982
        const val message: String = "M-SEARCH * HTTP/1.1\r\n" +
                "HOST:239.255.255.250:1982\r\n" +
                "MAN:\"ssdp:discover\"\r\n" +
                "ST:wifi_bulb\r\n"
    }

    private var loadingDialog :AlertDialog? = null
    private val listOfDevices = ArrayList<HashMap<String,String>>()
    private lateinit var multicastLock: WifiManager.MulticastLock
    private lateinit var searchThread:Thread
    private var isSearching = true
    private val mHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                MSG_FOUND_DEVICE -> {
                    loadingDialog?.dismiss()
                    Helpers().getDevicesDialog(requireActivity(),requireContext(),"Choose Device",listOfDevices)
                }
                MSG_SHOWLOG -> Toast.makeText(requireContext(), "" + msg.obj.toString(), Toast.LENGTH_SHORT).show()
                MSG_STOP_SEARCH -> {
                    loadingDialog?.dismiss()
                    searchThread.interrupt()
                    Helpers().getDevicesDialog(requireActivity(),requireContext(),"Choose Device",listOfDevices)
                    isSearching = false
                }
                MSG_DISCOVER_FINISH -> {
                    loadingDialog?.dismiss()
                    Helpers().getDevicesDialog(requireActivity(),requireContext(),"Choose Device",listOfDevices)
                }
            }
        }
    }
    private val bulbsViewModel: BulbsViewModel by viewModels {
        BulbsViewModelFactory((requireActivity().application as YeebumApplication).bulbsRepository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_enter_bulb_data, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = LoadingDialog.getDialog(requireContext(),"Searching For Devices...")

        val wm = requireActivity().applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        multicastLock = wm.createMulticastLock("yeebum")
        multicastLock.acquire()
        searchForDevices()

        setSpannableText()
        enterBulbDataBackButton.setOnClickListener {
            requireActivity().onBackPressed()          
        }


        saveBulbButton.setOnClickListener {
            saveBulb()
        }
    }



    //set part of text more bold
    private fun setSpannableText(){
        val text :SpannableString = SpannableString(checkText.text)
        text.setSpan(
            ForegroundColorSpan(Color.WHITE),
            23, // start
            33, // end
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        text.setSpan(
            StyleSpan(Typeface.BOLD),
            23, // start
            33, // end
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        checkText.text = text
        checkText.setOnClickListener {findNavController().navigate(EnterBulbDataFragmentDirections.actionEnterBulbDataFragmentToHelpFragment()) }
    }



    //-----------------------------| Save bulb into database |-----------------------------------
    private fun saveBulb(){
        val name = if(bulbNameInput.text.isNullOrEmpty()) "My Yeelight" else bulbNameInput.text.toString()
        val ip =  bulbIpInput.text.toString()
        val port = try {bulbPortInput.text.toString().toInt() }catch (ex:Exception){0}
        if(ip.contains("192.168") && port>10000){
            bulbsViewModel.insertBulb(Bulb(name,ip,port))
            requireActivity().onBackPressed()
        }
        else
            Helpers().showSnackBar(requireView(),"Check your port and ip format",null,null)
    }
    //===========================================================================================


    private fun searchForDevices(){

        loadingDialog?.show()

        listOfDevices.clear()
        searchThread = Thread {
            val mDSocket = DatagramSocket()
            val dpSend = DatagramPacket(message.toByteArray(), message.toByteArray().size, InetAddress.getByName(UDP_HOST), UDP_PORT)
            mDSocket.send(dpSend)
            mHandler.sendEmptyMessageDelayed(MSG_STOP_SEARCH, 2000)
            while (isSearching){
                val buf = ByteArray(1024)
                val dpReceive = DatagramPacket(buf, buf.size)
                mDSocket.receive(dpReceive)
                val bytes = dpReceive.data
                val buffer = StringBuffer()
                for(i in 0 until dpReceive.length){
                    if(bytes[i] == 13.toByte()){
                        continue
                    }
                    buffer.append(bytes[i].toChar())
                }

                if(!buffer.toString().contains("yeelight")){
                    mHandler.obtainMessage(MSG_SHOWLOG, "XD").sendToTarget()
                    return@Thread
                }

                val infos = buffer.toString().split("\n")
                val bulbInfoHashMap = HashMap<String, String>()
                for(i in infos) {
                    val index = i.indexOf(":")
                    if(index==-1) continue
                    val title = i.substring(0, index)
                    val value = i.substring(index + 1)
                    bulbInfoHashMap.put(title, value)
                }
                if(!hasAdd(bulbInfoHashMap)){
                    listOfDevices.add(bulbInfoHashMap)
                }
            }
            mHandler.sendEmptyMessage(MSG_DISCOVER_FINISH)
        }
        searchThread.start()


    }

    
    private fun hasAdd(bulbInfo: HashMap<String, String>): Boolean {
        listOfDevices.forEach {
            if (it["Location"].equals(bulbInfo["Location"])) {
                return true
            }
        }
        return false
    }


}