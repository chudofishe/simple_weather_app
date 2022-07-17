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
import com.chudofishe.weatherapp.R
import com.chudofishe.weatherapp.common.Result
import com.chudofishe.weatherapp.databinding.FragmentCurrentWeatherBinding
import com.chudofishe.weatherapp.ui.main.MainViewModel
import kotlinx.coroutines.launch

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
                                    temp.text = cw.temp.toString()
                                    text.text = cw.conditionText
                                    feelsLike.text = getString(R.string.feels_like, cw.feelsLike.toString())
                                }
                            }
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