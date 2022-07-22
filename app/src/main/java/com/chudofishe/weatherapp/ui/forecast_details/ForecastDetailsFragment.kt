package com.chudofishe.weatherapp.ui.forecast_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.chudofishe.weatherapp.databinding.FragmentForecastDetailsBinding
import kotlinx.coroutines.launch

class ForecastDetailsFragment : Fragment() {

    private var _binding: FragmentForecastDetailsBinding? = null
    private val binding: FragmentForecastDetailsBinding
        get() = _binding!!

    private val viewModel: ForecastDetailsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentForecastDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.forecastDetails.collect {

                }
            }
        }
    }

}