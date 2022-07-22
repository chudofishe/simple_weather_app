package com.chudofishe.weatherapp.ui.forecast_list

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
import com.chudofishe.weatherapp.common.Result
import com.chudofishe.weatherapp.databinding.FragmentForecastListBinding
import com.chudofishe.weatherapp.domain.model.Forecast
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
                viewModel.forecastState.collect { result ->
                    when (result) {
                        is Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.list.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                            binding.list.adapter = ForecastListAdapter(result.data ?: emptyList())
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

    private fun setupList(data: List<Forecast>) {
        val adapter = ForecastListAdapter(data)
        adapter.clickListener = {
            val action = ForecastListFragmentDirections.actionForecastListFragmentToCurrentWeatherFragment(it)
            this.findNavController().navigate(action)
        }
        binding.list.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            this.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}