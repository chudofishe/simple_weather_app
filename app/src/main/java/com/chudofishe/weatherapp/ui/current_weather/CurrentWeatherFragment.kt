package com.chudofishe.weatherapp.ui.current_weather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.chudofishe.weatherapp.R
import com.chudofishe.weatherapp.common.Result
import com.chudofishe.weatherapp.common.toIntString
import com.chudofishe.weatherapp.common.toPercent
import com.chudofishe.weatherapp.databinding.FragmentCurrentWeatherBinding
import com.chudofishe.weatherapp.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CurrentWeatherFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    private var _binding: FragmentCurrentWeatherBinding? = null
    private val binding: FragmentCurrentWeatherBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentWeatherState.collect { result ->
                    when (result) {
                        is Result.Loading -> {
                            binding.viewGroup.visibility = View.GONE
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            result.data?.let { cw ->
                                binding.apply {
                                    temp.text = getString(R.string.temperature, cw.temp)
                                    conditionText.text = cw.conditionText
                                    feelsLike.text = getString(R.string.feels_like, cw.feelsLike.toInt())
                                    uv.text = cw.uv.toString()
                                    windKph.text = cw.windKph.toString()
                                    humidity.text = cw.humidity.toString()
                                    cloud.text = cw.cloud.toString()
                                    activity?.let { Glide.with(it).load(cw.conditionIcon).into(icon) }
                                }
                            }
                            binding.viewGroup.visibility = View.VISIBLE
                            binding.progressBar.visibility = View.GONE
                        }
                        else -> {
                            //show error message
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                requireContext(),
                                result.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}