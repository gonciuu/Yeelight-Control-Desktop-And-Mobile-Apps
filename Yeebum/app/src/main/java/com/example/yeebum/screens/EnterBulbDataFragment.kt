package com.example.yeebum.screens

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
import android.widget.TextView
import com.example.yeebum.R
import kotlinx.android.synthetic.main.fragment_enter_bulb_data.*


class EnterBulbDataFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enter_bulb_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        setSpannableText()

    }



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
    }

}