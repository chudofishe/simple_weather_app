package com.chudofishe.weatherapp.ui.forecast_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.chudofishe.weatherapp.R
import com.chudofishe.weatherapp.databinding.FragmentForecastDetailsBinding
import com.chudofishe.weatherapp.domain.model.ForecastDetails
import java.time.LocalDate

class ForecastDetailsFragment : Fragment() {

    private var _binding: FragmentForecastDetailsBinding? = null
    private val binding: FragmentForecastDetailsBinding
        get() = _binding!!

    private val navArgs: ForecastDetailsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentForecastDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val forecast = navArgs.forecastDetails
        val dateEpoch = LocalDate.ofEpochDay(forecast.dateEpoch.toLong())
        binding.apply {
            date.text = getString(R.string.month_date, dateEpoch.month.toString(), dateEpoch.dayOfMonth)
            temp.text = getString(R.string.temperature, forecast.avgTemp.toInt())
            conditionText.text = forecast.text
            uv.text = forecast.uv.toString()
            windKph.text = forecast.maxWind.toString()
            humidity.text = forecast.avgHumidity.toString()
            hourList.adapter = HourLIstAdapter(forecast.hourForecastList)
            activity?.let { Glide.with(it).load(forecast.icon).into(icon) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}