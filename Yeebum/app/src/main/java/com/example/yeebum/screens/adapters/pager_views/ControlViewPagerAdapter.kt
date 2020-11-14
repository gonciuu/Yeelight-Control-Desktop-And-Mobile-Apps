package com.example.yeebum.screens.adapters.pager_views

import androidx.fragment.app.*
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.yeebum.screens.ColorControlFragment
import com.example.yeebum.screens.FlowControlFragment

class ControlViewPagerAdapter(fa:FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment = if (position==0) ColorControlFragment() else FlowControlFragment()


}