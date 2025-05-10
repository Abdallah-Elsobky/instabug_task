package com.example.instabug_task.ui.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.instabug_task.databinding.FragmentForecastBinding
import com.example.instabug_task.domain.model.Weather
import com.example.instabug_task.domain.usecase.GetWeatherDataUseCase
import com.example.instabug_task.ui.veiwmodel.WeatherViewModel
import com.example.instabug_task.ui.veiwmodel.WeatherViewModelFactory
import com.example.instabug_task.data.repository.WeatherRepositoryImp
import com.example.instabug_task.data.mapper.WeatherMapper
import com.example.instabug_task.data.utils.NetworkChecker
import com.example.instabug_task.domain.usecase.GetCurrentLocationUseCase
import com.example.instabug_task.data.repository.LocationRepositoryImpl
import com.example.instabug_task.ui.permission.PermissionHandler
import androidx.activity.result.contract.ActivityResultContracts
import com.example.instabug_task.data.datasource.LocalDataSource
import com.example.instabug_task.data.datasource.RemoteDataSource
import com.example.instabug_task.data.location.LocationHelper

class ForecastFragment : Fragment() {

    private var _binding: FragmentForecastBinding? = null
    private val binding get() = _binding!!

    private lateinit var locationHelper: LocationHelper
    private lateinit var permissionHandler: PermissionHandler
    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            locationHelper.getLocationString { location ->
                viewModel.getWeather()
            }
        } else {
            permissionHandler.showExplanationDialog()
        }
    }

    private val viewModel: WeatherViewModel by activityViewModels {
        val locationRepository = LocationRepositoryImpl(locationHelper)
        val getLocationUseCase = GetCurrentLocationUseCase(locationRepository)

        val weatherRepository = WeatherRepositoryImp(
            remoteDataSource = RemoteDataSource(),
            localDataSource = LocalDataSource(requireContext()),
            weatherMapper = WeatherMapper(),
            networkChecker = NetworkChecker(requireContext())
        )

        val getWeatherDataUseCase = GetWeatherDataUseCase(weatherRepository, getLocationUseCase)

        WeatherViewModelFactory(getWeatherDataUseCase)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForecastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPermissionAndLocation()

        viewModel.weatherLiveData.observe(viewLifecycleOwner) { weather ->
            if (weather != null) {
                updateUI(weather)
            } else {
                showEmptyState()
            }
        }

        viewModel.getWeather()
    }

    private fun setupPermissionAndLocation() {
        permissionHandler = PermissionHandler(
            context = requireContext(),
            requestLocationPermissionLauncher = locationPermissionLauncher
        )

        locationHelper = LocationHelper(
            context = requireContext(),
            permissionHandler = permissionHandler
        )
    }

    private fun updateUI(weather: Weather) {
        val forecastDays = weather.days
        binding.forecastRv.adapter = ForecastAdapter(forecastDays)
    }

    private fun showEmptyState() {
        binding.forecastRv.adapter = ForecastAdapter(emptyList())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
