package com.example.youtubetest.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.youtubetest.bottomtabs.tabsfragments.HistoryFragment
import com.example.youtubetest.bottomtabs.tabsfragments.SearchFragment

class TapPageAdapter(activity: FragmentActivity, private val tabCount: Int) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = tabCount

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SearchFragment()
            1->  HistoryFragment()
            else -> SearchFragment()
        }
    }
}