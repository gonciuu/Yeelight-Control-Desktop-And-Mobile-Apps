package com.example.yeebum.screens.bulbs

import android.annotation.SuppressLint
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
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.yeebum.R
import com.example.yeebum.YeebumApplication
import com.example.yeebum.databases.bulbs_database.BulbsViewModel
import com.example.yeebum.databases.bulbs_database.BulbsViewModelFactory
import com.example.yeebum.models.Bulb
import com.example.yeebum.screens.components.Helpers
import com.example.yeebum.screens.components.LoadingDialog
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_all_bulbs.*
import kotlinx.android.synthetic.main.fragment_enter_bulb_data.*
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress


class EnterBulbDataFragment : Fragment() ,SearchedBulbsInterface{

    //bulb message info to get the available bulbs
    companion object {
        const val MSG_STOP_SEARCH = 3
        const val UDP_HOST = "239.255.255.250"
        const val UDP_PORT = 1982
        const val message: String = "M-SEARCH * HTTP/1.1\r\n" +
                "HOST:239.255.255.250:1982\r\n" +
                "MAN:\"ssdp:discover\"\r\n" +
                "ST:wifi_bulb\r\n"
    }

    //loading dialog
    private var loadingDialog: AlertDialog? = null
    private val helpers = Helpers()

    //get the bulbs
    private val listOfDevices = ArrayList<HashMap<String, String>>()
    private lateinit var multicastLock: WifiManager.MulticastLock
    private lateinit var searchThread: Thread
    private var isSearching = true

    //receive message to stop the searching
    private val mHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            loadingDialog?.dismiss()
            searchThread.interrupt()
            showDevicesDialog()
            isSearching = false
        }
    }

    private val bulbsViewModel: BulbsViewModel by viewModels {
        BulbsViewModelFactory((requireActivity().application as YeebumApplication).bulbsRepository)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_enter_bulb_data, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = LoadingDialog.getDialog(requireContext(), "Searching For Devices...")

        //turn on the multicast lock
        val wm = requireActivity().applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        multicastLock = wm.createMulticastLock("yeebum")
        multicastLock.acquire()
        setupStartData()
        searchForDevices(2000)

        setSpannableText()
        enterBulbDataBackButton.setOnClickListener {
            requireActivity().onBackPressed()
        }


        saveBulbButton.setOnClickListener {
            saveBulb()
        }


    }


    //set part of text more bold
    private fun setSpannableText() {
        val text = SpannableString(checkText.text)
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
        checkText.setOnClickListener { findNavController().navigate(EnterBulbDataFragmentDirections.actionEnterBulbDataFragmentToHelpFragment()) }
    }


    //-----------------------------| Save bulb into database |-----------------------------------
    private fun saveBulb() {
        val name = if (bulbNameInput.text.isNullOrEmpty()) "My Yeelight" else bulbNameInput.text.toString().trim()
        val ip = bulbIpInput.text.toString().trim()
        val port = try {
            bulbPortInput.text.toString().trim().toInt()
        } catch (ex: Exception) {
            0
        }

            if (ip.contains("192.168") && port > 10000) {
                if(requireArguments().isEmpty){
                    bulbsViewModel.insertBulb(Bulb(name, ip, port))
                }else{
                    val bulb = Gson().fromJson(requireArguments().getString("bulb"),Bulb::class.java)
                    bulb.ip = ip
                    bulb.port = port
                    bulb.name = name
                    bulbsViewModel.updateBulb(bulb)
                }
                requireActivity().onBackPressed()
            } else
                helpers.showSnackBar(requireView(), "Check your port and ip format", null, null)


    }
    //===========================================================================================


    //--------------------------------------------| Search for bulbs to pare |--------------------------------------
    private fun searchForDevices(delay:Long) {
        loadingDialog?.show()

        searchThread = Thread {
            try{
                val mDSocket = DatagramSocket()
                val dpSend = DatagramPacket(
                    message.toByteArray(),
                    message.toByteArray().size,
                    InetAddress.getByName(UDP_HOST),
                    UDP_PORT
                )
                mDSocket.send(dpSend)
                mHandler.sendEmptyMessageDelayed(MSG_STOP_SEARCH, delay)
                while (isSearching) {
                    val buf = ByteArray(1024)
                    val dpReceive = DatagramPacket(buf, buf.size)
                    mDSocket.receive(dpReceive)
                    val bytes = dpReceive.data
                    val buffer = StringBuffer()
                    for (i in 0 until dpReceive.length) {
                        if (bytes[i] == 13.toByte()) {
                            continue
                        }
                        buffer.append(bytes[i].toChar())
                    }

                    if (!buffer.toString().contains("yeelight")) {
                        loadingDialog?.dismiss()
                        helpers.showSnackBar(requireView(), "Cannot find any bulbs", null, null)
                        return@Thread
                    }

                    val infos = buffer.toString().split("\n")
                    val bulbInfoHashMap = HashMap<String, String>()
                    for (i in infos) {
                        val index = i.indexOf(":")
                        if (index == -1) continue
                        val title = i.substring(0, index)
                        val value = i.substring(index + 1)
                        bulbInfoHashMap[title] = value
                    }
                    if (!hasAdd(bulbInfoHashMap)) {
                        listOfDevices.add(bulbInfoHashMap)
                    }
                }
            }catch (ex:Exception){
                requireActivity().runOnUiThread {
                    helpers.showSnackBar(requireView(),ex.message.toString(),null,null)
                    loadingDialog?.dismiss()
                    requireActivity().onBackPressed()
                }
            }

        }
        searchThread.start()
    }

    //=================================================================================================================

    //---------------------| Check if the bulb is already in the list |------------------------
    private fun hasAdd(bulbInfo: HashMap<String, String>): Boolean {
        listOfDevices.forEach {
            if (it["Location"].equals(bulbInfo["Location"])) {
                return true
            }
        }
        return false
    }
    //=========================================================================================


    //show list of devices dialog
    private fun showDevicesDialog() = helpers.getDevicesDialog(
        requireActivity(),
        requireContext(),
        "Choose Device",
        listOfDevices, this
    )


    // release the multicast lock
    override fun onDestroy() {
        super.onDestroy()
        multicastLock.release()
    }


    //------------------------| Set texts inputs on choose the bulb |---------------------------
    @SuppressLint("SetTextI18n")
    override fun onChooseBulb(bulbInfo: HashMap<String, String>) {
        val ipInfo = bulbInfo["Location"]!!.split("//")[1]
        bulbNameInput.setText("My Yeelight${bulbInfo["model"]}")
        bulbIpInput.setText(ipInfo.split(":".toRegex()).toTypedArray()[0])
        bulbPortInput.setText(ipInfo.split(":".toRegex()).toTypedArray()[1])
    }
    //=========================================================================================

    override fun onRefresh() = searchForDevices(4000)


    //--------------------------------setup start data in edittexts-----------------------
    private fun setupStartData(){
        if(!requireArguments().isEmpty){
            val bulb = Gson().fromJson(requireArguments().getString("bulb"),Bulb::class.java)
            bulbNameInput.setText(bulb.name)
            bulbPortInput.setText(bulb.port.toString())
            bulbIpInput.setText(bulb.ip)
        }
    }
    //====================================================================================

}