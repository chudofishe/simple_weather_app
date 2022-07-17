package com.chudofishe.weatherapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.chudofishe.weatherapp.databinding.FragmentMainBinding
import com.chudofishe.weatherapp.ui.current_weather.CurrentWeatherFragment
import com.chudofishe.weatherapp.ui.forecast_list.ForecastListFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment() {

    private lateinit var _binding: FragmentMainBinding
    private val binding
        get() = _binding

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.let {
            viewPager = it.viewPager
            tabLayout = it.tabLayout
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPager.adapter = Adapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Now"
                else -> "Forecast"
            }
        }.attach()
    }

    inner class Adapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            val fragment = when (position) {
                0 -> CurrentWeatherFragment()
                else -> ForecastListFragment()
            }

            return fragment
        }
    }
}