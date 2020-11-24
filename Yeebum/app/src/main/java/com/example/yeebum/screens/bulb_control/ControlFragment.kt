package com.example.yeebum.screens.bulb_control

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.yeebum.R
import com.example.yeebum.control_bulb.BulbControlViewModel
import com.example.yeebum.screens.components.AppDrawer
import com.example.yeebum.screens.adapters.pager_views.ControlViewPagerAdapter
import com.example.yeebum.screens.components.Helpers
import com.example.yeebum.screens.components.LoadingDialog
import kotlinx.android.synthetic.main.fragment_control.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedOutputStream
import java.net.ConnectException
import java.net.Socket


class ControlFragment : Fragment() {
    private lateinit var socket: Socket
    private lateinit var bulbControlViewModel: BulbControlViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_control, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bulbControlViewModel = ViewModelProvider(requireActivity())[BulbControlViewModel::class.java]


        controlViewPager.adapter = ControlViewPagerAdapter(requireActivity())
        setupViewPager()

        controlSettingsButton.setOnClickListener { findNavController().navigate(ControlFragmentDirections.actionControlFragmentToSettingsFragment()) }
        AppDrawer().setOpenDrawer(controlDrawerButton,requireActivity())

        connectToBulb()
    }


    //-------------------------------| Setup view pager and texts with it |----------------------------------

    private fun setupViewPager(){
        val listOfTexts = arrayListOf(colorText, flowsText)
        //change pager view on text click
        listOfTexts.forEach { clickedTV->
            clickedTV.setOnClickListener {
                setTextsColor(listOfTexts, listOfTexts.indexOf(clickedTV))
                controlViewPager.currentItem = listOfTexts.indexOf(clickedTV)
            }
        }

        //set text color on change pager view
        controlViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                setTextsColor(listOfTexts, position)
                super.onPageSelected(position)
            }
        })

        //---------change sensitive---------
        val recyclerViewField = ViewPager2::class.java.getDeclaredField("mRecyclerView")
        recyclerViewField.isAccessible = true
        val recyclerView = recyclerViewField[controlViewPager] as RecyclerView
        val touchSlopField = RecyclerView::class.java.getDeclaredField("mTouchSlop")
        touchSlopField.isAccessible = true
        val touchSlop = touchSlopField[recyclerView] as Int
        touchSlopField[recyclerView] = touchSlop
        //=====================================


    }

    private fun setTextsColor(listOfTexts: ArrayList<TextView>, position: Int){
        listOfTexts.forEach { textView -> textView.setTextColor(Color.parseColor("#44FFFFFF")) }
        listOfTexts[position].setTextColor(Color.parseColor("#FFFFFF"))
    }

    //=========================================================================================================


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
                requireActivity().runOnUiThread {
                    bulbControlViewModel.setSocket(socket)
                    bulbControlViewModel.setBOS(BufferedOutputStream(socket.getOutputStream()))
                }
            } catch (connectEx: ConnectException) {
                helpers.showSnackBar(
                    requireView(),
                    "Error! Check your internet connection.",
                    null,
                    null
                )
            } catch (ex: Exception) {
                helpers.showSnackBar(requireView(), ex.message!!, null, null)
            }
            dialog.dismiss()
        }
    }
    //========================================================================================

}