package com.example.yeebum.screens.other

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import com.example.yeebum.R
import com.example.yeebum.screens.components.Prefs
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_settings, container, false)


    private val prefs = Prefs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingsBackButton.setOnClickListener { requireActivity().onBackPressed() }
        setupSwitchValues()
        setupSwitchesOnChange()
    }


    //------------------------------| Save state to shared prefs on switch changed |-------------------------------
    private fun setupSwitchesOnChange() {
        val listOfSwitches = arrayListOf<SwitchCompat>(showBulbNameSwitch, showBulbPortSwitch, showBulbIpSwitch)
        val listOfPrefsName = arrayListOf("showName", "showPort", "showIp")
        listOfSwitches.forEach {
            it.setOnCheckedChangeListener { _, isChecked ->
                prefs.setField(requireActivity(), listOfPrefsName[listOfSwitches.indexOf(it)], isChecked)
            }
        }
    }
    //=============================================================================================================


    //----------------------------------| setup switch start check state |-----------------------------------
    private fun setupSwitchValues() {
        val settings = prefs.getSettings(requireActivity())
        showBulbNameSwitch.isChecked = settings["showName"]!!
        showBulbPortSwitch.isChecked = settings["showPort"]!!
        showBulbIpSwitch.isChecked = settings["showIp"]!!
    }
    //========================================================================================================

}