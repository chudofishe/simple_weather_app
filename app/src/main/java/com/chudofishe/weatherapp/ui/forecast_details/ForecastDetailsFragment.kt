package com.chudofishe.weatherapp.ui.forecast_details

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
import com.bumptech.glide.Glide
import com.chudofishe.weatherapp.R
import com.chudofishe.weatherapp.common.Result
import com.chudofishe.weatherapp.databinding.FragmentForecastDetailsBinding
import com.chudofishe.weatherapp.domain.model.ForecastDetails
import kotlinx.coroutines.launch
import java.time.LocalDate

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
                viewModel.forecastDetails.collect { result ->
                    when (result) {
                        is Result.Success -> result.data?.let { assignData(it) }
                        else -> showErrorMessage(result.message ?: "Unknown error occurred")
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun assignData(fd: ForecastDetails) {
        val dateEpoch = LocalDate.ofEpochDay(fd.dateEpoch.toLong())
        binding.apply {
            date.text = getString(R.string.month_date, dateEpoch.month.toString(), dateEpoch.dayOfMonth)
            temp.text = getString(R.string.temperature, fd.avgTemp.toInt())
            conditionText.text = fd.text
            uv.text = fd.uv.toString()
            windKph.text = fd.maxWind.toString()
            humidity.text = fd.avgHumidity.toString()
            activity?.let { Glide.with(it).load(fd.icon).into(icon) }
            hourList.apply {
                adapter = HourLIstAdapter(fd.hourForecastList)
            }
        }
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

}