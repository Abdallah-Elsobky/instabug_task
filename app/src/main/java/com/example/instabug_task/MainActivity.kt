package com.example.instabug_task

import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.instabug_task.data.datasourse.LocalDataSource
import com.example.instabug_task.data.mapper.WeatherMapper
import com.example.instabug_task.data.repository.WeatherRepositoryImp
import com.example.instabug_task.data.utils.NetworkChecker
import com.example.instabug_task.databinding.ActivityMainBinding
import com.example.instabug_task.location.LocationHelper
import com.example.instabug_task.ui.permission.PermissionHandler
import com.example.instabugtask.data.datasource.RemoteDataSource

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var locationHelper: LocationHelper
    private lateinit var permissionHandler: PermissionHandler

    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            locationHelper.getLocationString { location ->
                if (location != null) {
                    Log.d("MainActivity", "Location: $location")
                }
            }
        } else {
            permissionHandler.showExplanationDialog()
        }
    }

    private val resolutionLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                locationHelper.getLocationString { location -> }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        permissionHandler = PermissionHandler(this, locationPermissionLauncher)
        locationHelper = LocationHelper(this, permissionHandler)
        locationHelper.setResolutionLauncher(resolutionLauncher)
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_home)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.container.setOnClickListener {
//            navController.navigate(R.id.navigation_dashboard)
//            locationHelper.getLocationString {
//                Thread {
//                    val result = WeatherRepositoryImp(
//                        RemoteDataSource(),
//                        LocalDataSource(this),
//                        WeatherMapper(),
//                        NetworkChecker(this)
//                    ).getWeather(it.toString())
//                    Log.e("testo", result.currentConditions?.datetime.toString())
//                }.start()
//            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
