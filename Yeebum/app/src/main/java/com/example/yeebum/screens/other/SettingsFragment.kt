package com.example.yeebum.screens.other

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.yeebum.BuildConfig
import com.example.yeebum.R
import com.example.yeebum.YeebumApplication
import com.example.yeebum.databases.bulbs_database.BulbsViewModel
import com.example.yeebum.databases.bulbs_database.BulbsViewModelFactory
import com.example.yeebum.databases.flows_database.FlowsViewModel
import com.example.yeebum.databases.flows_database.FlowsViewModelFactory
import com.example.yeebum.screens.components.Helpers
import com.example.yeebum.screens.components.Prefs
import kotlinx.android.synthetic.main.fragment_settings.*
import java.lang.Exception


class SettingsFragment : Fragment(),SettingsInterface {
    private val bulbsViewModel: BulbsViewModel by viewModels {
        BulbsViewModelFactory((requireActivity().application as YeebumApplication).bulbsRepository)
    }

    private val flowsViewModel: FlowsViewModel by viewModels {
        FlowsViewModelFactory((requireActivity().application as YeebumApplication).flowsRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_settings, container, false)


    private val prefs = Prefs()
    private val helpers = Helpers()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingsBackButton.setOnClickListener { requireActivity().onBackPressed() }
        setupSwitchValues()
        setupSwitchesOnChange()
        setupFactorySettings()

        //show app version
        showAppVersion()
    }


    //------------------------------| Save state to shared prefs on switch changed |-------------------------------
    private fun setupSwitchesOnChange() {
        val listOfSwitches = arrayListOf<SwitchCompat>(
            showBulbNameSwitch,
            showBulbPortSwitch,
            showBulbIpSwitch
        )
        val listOfPrefsName = arrayListOf("showName", "showPort", "showIp")
        listOfSwitches.forEach {
            it.setOnCheckedChangeListener { _, isChecked ->
                prefs.setField(
                    requireActivity(),
                    listOfPrefsName[listOfSwitches.indexOf(it)],
                    isChecked
                )
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

    //-----------------------------| Show App version in snackbar |-------------------------
    private fun showAppVersion(){
        appVersionBox.setOnClickListener {
            try {
                helpers.showSnackBar(requireView(),"App version : ${BuildConfig.VERSION_NAME}",null,null)
            } catch (e: PackageManager.NameNotFoundException) {
                helpers.showSnackBar(requireView(),"Cannot find app version",null,null)
            }catch (ex:Exception){
                helpers.showSnackBar(requireView(),ex.message.toString(),null,null)
            }
        }
    }
    //=======================================================================================

    private fun setupFactorySettings(){
        factorySettingsBox.setOnClickListener {
            helpers.getConfirmFactorySettingsDialog(requireActivity(),requireContext(),this).show()
        }
    }

    override fun setFactorySettings() {
        prefs.apply {
            setField(requireActivity(),"showName",true)
            setField(requireActivity(),"showIp",true)
            setField(requireActivity(),"showPort",true)
        }
        bulbsViewModel.deleteAllBulbs()
        flowsViewModel.deleteAllFlows()
        helpers.showSnackBar(requireView(),"Factory settings has been set",null,null)
        requireActivity().onBackPressed()
    }

}