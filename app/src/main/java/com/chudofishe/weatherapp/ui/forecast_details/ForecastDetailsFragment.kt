package com.chudofishe.weatherapp.ui.forecast_details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chudofishe.weatherapp.R

class ForecastDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = ForecastDetailsFragment()
    }

    private lateinit var viewModel: ForecastDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_forecast_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ForecastDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}