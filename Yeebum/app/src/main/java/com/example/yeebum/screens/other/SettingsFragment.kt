package com.example.yeebum.screens.other

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import com.example.yeebum.R
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_settings, container, false)




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingsBackButton.setOnClickListener { requireActivity().onBackPressed() }
        setupSwitchValues()
        setupSwitchesOnChange()
    }


    //------------------------------| Save state to shared prefs on switch changed |-------------------------------
    private fun setupSwitchesOnChange(){
        val prefs = requireActivity().getSharedPreferences("OPTIONS",MODE_PRIVATE)
        val listOfSwitches = arrayListOf<SwitchCompat>(showBulbNameSwitch,showBulbPortSwitch,showBulbIpSwitch)
        val listOfPrefsName = arrayListOf<String>("showName","showPort","showIp")
        listOfSwitches.forEach {
            it.setOnCheckedChangeListener{ _, isChecked->
                prefs.edit().apply {
                    putBoolean(listOfPrefsName[listOfSwitches.indexOf(it)],isChecked)
                    apply()
                }
            }
        }
    }
    //=============================================================================================================


    //----------------------------------| setup switch start check state |-----------------------------------
    private fun setupSwitchValues(){
        val prefs = requireActivity().getSharedPreferences("OPTIONS",MODE_PRIVATE)
        prefs.apply {
            showBulbNameSwitch.isChecked = getBoolean("showName", true)
            showBulbPortSwitch.isChecked = getBoolean("showPort", true)
            showBulbIpSwitch.isChecked = getBoolean("showIp", true)
        }
    }
    //========================================================================================================

}