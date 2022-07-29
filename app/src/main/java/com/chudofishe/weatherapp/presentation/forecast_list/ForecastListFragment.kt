package com.chudofishe.weatherapp.presentation.forecast_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chudofishe.weatherapp.common.Constants
import com.chudofishe.weatherapp.common.Result
import com.chudofishe.weatherapp.databinding.FragmentForecastListBinding
import com.chudofishe.weatherapp.domain.model.Forecast
import com.chudofishe.weatherapp.domain.model.ForecastDetails
import com.chudofishe.weatherapp.presentation.main.MainFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForecastListFragment : Fragment() {

    private val viewModel: ForecastListFragmentViewModel by viewModels()

    private var _binding: FragmentForecastListBinding? = null
    private val binding: FragmentForecastListBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForecastListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.forecastListState.collect { result ->
                    when (result) {
                        is Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            binding.progressBar.visibility = View.GONE
                            setupList(result.data ?: emptyList())
                        }
                        else -> {
                            binding.progressBar.visibility = View.GONE
                            showErrorMessage(result.message ?: "Unknown error occurred")
                        }
                    }
                }
            }
        }

        arguments?.let {
            viewModel.getCurrentWeather(
                it.getDouble(Constants.KEY_LATITUDE),
                it.getDouble(Constants.KEY_LONGITUDE)
            )
        }
    }

    private fun setupList(data: List<Forecast>) {
        val adapter = ForecastListAdapter(data, ::navigateToForecastDetails)
        binding.list.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            this.adapter = adapter
        }
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun navigateToForecastDetails(item: ForecastDetails) {
        val action = MainFragmentDirections.actionMainFragmentToCurrentWeatherFragment(item)
        this.findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}