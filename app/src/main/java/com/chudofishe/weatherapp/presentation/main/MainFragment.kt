package com.chudofishe.weatherapp.presentation.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.chudofishe.weatherapp.common.Constants.KEY_LATITUDE
import com.chudofishe.weatherapp.common.Constants.KEY_LONGITUDE
import com.chudofishe.weatherapp.databinding.FragmentMainBinding
import com.chudofishe.weatherapp.presentation.current_weather.CurrentWeatherFragment
import com.chudofishe.weatherapp.presentation.forecast_list.ForecastListFragment
import com.google.android.gms.location.*
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var tabLayoutMediator: TabLayoutMediator

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private enum class Cause {
        PERMISSION, LOCATION, NEVER_ASK
    }

    private val locationPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getCurrentLocation(getLocationResultListener)
            } else {
                val neverAskAgain = !ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                displayAlertDialog(if (neverAskAgain) Cause.NEVER_ASK else Cause.PERMISSION)
            }
    }

    private val getLocationResultListener = object : GetLocationResultListener {
        override fun onGetLocationSucceed(location: Location) {
            attachWeatherFragments(location)
        }

        override fun onGetLocationFailed() {
            displayAlertDialog(Cause.LOCATION)
        }
    }

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

        tabLayoutMediator = TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Now"
                else -> "Forecast"
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.let {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(it)
        }
    }

    override fun onStart() {
        super.onStart()
        if (!tabLayoutMediator.isAttached) {
            if (isLocationPermissionGranted()) {
                getCurrentLocation(getLocationResultListener)
            } else {
                locationPermissionRequest.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation(listener: GetLocationResultListener) {
        activity?.let {
            if (isLocationEnabled()) {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location == null) {
                        val locationRequest = LocationRequest.create()
                        val callback = object : LocationCallback() {
                            override fun onLocationResult(result: LocationResult) {
                                listener.onGetLocationSucceed(result.locations.last())
                            }
                        }
                        fusedLocationClient.requestLocationUpdates(locationRequest, callback, Looper.getMainLooper())
                    } else {
                        listener.onGetLocationSucceed(location)
                    }
                }
            } else {
                listener.onGetLocationFailed()
            }
        }
    }

    private fun attachWeatherFragments(location: Location) {
        viewPager.adapter = Adapter(this, location)
        tabLayoutMediator.attach()
    }

    private fun displayAlertDialog(cause: Cause) {
        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it).apply {
                when (cause) {
                    Cause.PERMISSION -> {
                        setMessage("Please provide location access")
                        setPositiveButton("Ok") { _, _ ->
                            locationPermissionRequest.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                        }
                    }
                    Cause.LOCATION -> {
                        setMessage("Please turn on location service")
                        setPositiveButton("Ok") { _, _ ->
                            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                            startActivity(intent)
                        }
                    }
                    Cause.NEVER_ASK -> {
                        setMessage("Sorry, this app requires location permission. Please grant access in settings")
                        setPositiveButton("Ok") {_, _ ->}
                    }
                }
            }
            builder.create()
        }
        alertDialog?.show()
    }

    private fun isLocationPermissionGranted() =
        ContextCompat.checkSelfPermission(requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class Adapter(fragment: Fragment, private val location: Location) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            val args = Bundle().apply {
                putDouble(KEY_LATITUDE, location.latitude)
                putDouble(KEY_LONGITUDE, location.longitude)
            }
            val fragment = when (position) {
                0 -> CurrentWeatherFragment()
                else -> ForecastListFragment()
            }.apply { arguments = args }

            return fragment
        }
    }
}