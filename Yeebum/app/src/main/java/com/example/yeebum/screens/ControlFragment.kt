package com.example.yeebum.screens

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.yeebum.R
import com.example.yeebum.screens.adapters.pager_views.ControlViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_control.*


class ControlFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_control, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controlViewPager.adapter = ControlViewPagerAdapter(requireActivity())
        setupViewPager()
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


}