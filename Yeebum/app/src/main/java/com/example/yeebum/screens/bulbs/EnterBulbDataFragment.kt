package com.example.yeebum.screens.bulbs

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.yeebum.R
import com.example.yeebum.YeebumApplication
import com.example.yeebum.databases.bulbs_database.*
import com.example.yeebum.models.Bulb
import com.example.yeebum.screens.components.Helpers
import kotlinx.android.synthetic.main.fragment_enter_bulb_data.*
import java.lang.Exception


class EnterBulbDataFragment : Fragment() {

    private val bulbsViewModel: BulbsViewModel by viewModels {
        BulbsViewModelFactory((requireActivity().application as YeebumApplication).bulbsRepository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enter_bulb_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



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





}