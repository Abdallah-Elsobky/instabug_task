package com.example.instabug_task.ui.home

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.instabug_task.R
import com.example.instabug_task.data.datasource.LocalDataSource
import com.example.instabug_task.data.datasource.RemoteDataSource
import com.example.instabug_task.data.location.LocationHelper
import com.example.instabug_task.data.mapper.WeatherMapper
import com.example.instabug_task.data.repository.LocationRepositoryImpl
import com.example.instabug_task.data.repository.WeatherRepositoryImp
import com.example.instabug_task.data.utils.NetworkChecker
import com.example.instabug_task.databinding.FragmentHomeBinding
import com.example.instabug_task.domain.model.Weather
import com.example.instabug_task.domain.usecase.GetCurrentLocationUseCase
import com.example.instabug_task.domain.usecase.GetWeatherDataUseCase
import com.example.instabug_task.ui.permission.PermissionHandler
import com.example.instabug_task.ui.veiwmodel.WeatherViewModel
import com.example.instabug_task.ui.veiwmodel.WeatherViewModelFactory
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var locationHelper: LocationHelper
    private lateinit var permissionHandler: PermissionHandler
    private lateinit var viewModel: WeatherViewModel

    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            locationHelper.getLocationString { location ->
                if (location != null) {
                    viewModel.getWeather()
                }
            }
        } else {
            permissionHandler.showExplanationDialog()
        }
    }

    private val resolutionLauncher = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            locationHelper.getLocationString { location -> }
        }
    }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPermissionAndLocation()
        setupViewModel()
        setupSwipeRefresh()
        navigateToForecast()
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
        locationHelper.setResolutionLauncher(resolutionLauncher)
    }

    private fun setupViewModel() {
        val locationRepository = LocationRepositoryImpl(locationHelper)
        val getLocationUseCase = GetCurrentLocationUseCase(locationRepository)

        val weatherRepository = WeatherRepositoryImp(
            remoteDataSource = RemoteDataSource(),
            localDataSource = LocalDataSource(requireContext()),
            weatherMapper = WeatherMapper(),
            networkChecker = NetworkChecker(requireContext())
        )

        val getWeatherDataUseCase = GetWeatherDataUseCase(
            weatherRepository,
            getLocationUseCase
        )


        val viewModelFactory = WeatherViewModelFactory(getWeatherDataUseCase)
        viewModel = ViewModelProvider(this, viewModelFactory).get(WeatherViewModel::class.java)

        viewModel.weatherLiveData.observe(viewLifecycleOwner) { weather ->
            if (weather != null) {
                updateUI(weather)
            } else {
                showEmptyState()
            }
            binding.swipeRefreshLayout.isRefreshing = false
        }
        viewModel.getWeather()
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            val isConnected = NetworkChecker(requireContext()).isNetworkAvailable()

            if (isConnected) {
                viewModel.getWeather()
            } else {
                binding.swipeRefreshLayout.isRefreshing = false
                showOfflineMessage()
            }
        }
    }

    private fun updateUI(weather: Weather) {
        binding.degree.text = (weather.currentConditions?.temp ?: 0.0).toString()
        binding.conditions.text = weather.currentConditions?.conditions.orEmpty()

        val firstDay = weather.days.firstOrNull()
        val maxTemp = firstDay?.tempmax ?: 0.0
        val minTemp = firstDay?.tempmin ?: 0.0
        binding.maxMin.text = buildString {
            append(maxTemp)
            append("°/")
            append(minTemp)
            append("°")
        }

        binding.lastUpdate.text = buildString {
            append("Updated at ")
            append(weather.lastUpdate ?: getString(R.string.unknown))
        }
        binding.country.text = weather.timezone ?: getString(R.string.unknown)

        val forecastDays = weather.days.take(3)
        binding.forecastRv.adapter = FiveForecastAdapter(forecastDays)
    }

    private fun showEmptyState() {
        binding.degree.text = "--"
        binding.conditions.text = "--"
        binding.maxMin.text = "--°/--°"
        binding.country.text = getString(R.string.unknown)
        binding.forecastRv.adapter = FiveForecastAdapter(emptyList())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToForecast() {
        val clickableViews =
            listOf(binding.forecastContainer, binding.forecastRv, binding.forecastBtn)
        clickableViews.forEach { view ->
            view.setOnClickListener {
                findNavController().navigate(R.id.navigation_dashboard)
            }
        }
    }

    private fun HomeFragment.showOfflineMessage() {
        Snackbar.make(binding.root, "No internet connection", Snackbar.LENGTH_SHORT).show()
    }
}


