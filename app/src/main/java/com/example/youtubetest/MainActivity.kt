package com.example.youtubetest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.youtubetest.adapters.TapPageAdapter
import com.example.youtubetest.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpTabBar()
    }

    private fun setUpTabBar() {
        val adapter = TapPageAdapter(this, binding.tabLayout.tabCount)
        binding.viewPager.adapter = adapter
        binding.viewPager.isUserInputEnabled=false
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tabLayout.selectTab((binding.tabLayout.getTabAt(position)))
            }
        })
        binding.tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.position.let { binding.viewPager.setCurrentItem(it, false) }
                binding.viewPager.currentItem = tab.position
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }
        })
    }

}
