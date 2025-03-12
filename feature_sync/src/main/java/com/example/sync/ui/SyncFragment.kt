package com.example.sync.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.sync.LocationWorker
import com.example.sync.R
import com.example.sync.databinding.FragmentSyncBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class SyncFragment : Fragment(R.layout.fragment_sync) {
    private var _binding: FragmentSyncBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SyncViewModel by viewModels()

    private val requestLocationPermissionLauncher: ActivityResultLauncher<String> = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            viewModel.updateLocationPermission(true)
        } else {
            viewModel.updateLocationPermission(false)
            Toast.makeText(requireContext(), "Permission Location denied", Toast.LENGTH_SHORT).show()
        }
    }
    private val requestNotificationPermissionLauncher: ActivityResultLauncher<String> = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            viewModel.updateNotificationPermission(true)
        } else {
            viewModel.updateNotificationPermission(false)
            Toast.makeText(requireContext(), "Permission Notification denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSyncBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun checkLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                viewModel.updateLocationPermission(true)
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                Toast.makeText(requireContext(), "Permission is needed get Location", Toast.LENGTH_SHORT).show()
                requestLocationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            else -> {
                requestLocationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    private fun checkNotificationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED -> {
                viewModel.updateNotificationPermission(true)
            }
            shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                Toast.makeText(requireContext(), "Permission is needed get Location", Toast.LENGTH_SHORT).show()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
            else -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }

    private fun startLocationUpdates() {
        val workRequest = PeriodicWorkRequestBuilder<LocationWorker>(
            15,
            TimeUnit.MINUTES
        )
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresBatteryNotLow(true)
                    .setRequiresCharging(false)
                    .build()
            )
            .build()

        WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
            "LocationWork",
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )

        workRequest.let {
            WorkManager.getInstance(requireContext()).getWorkInfoByIdLiveData(it.id).observeForever { workInfo ->
                if (workInfo != null) {
                    if (workInfo.state == WorkInfo.State.RUNNING) {
                        binding.workState.text = "${resources.getString(R.string.work_flow)} ${WorkInfo.State.RUNNING.name}"
                    } else {
                        binding.workState.text = "${resources.getString(R.string.work_flow)} ${workInfo.state}"
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkNotificationPermission()
        checkLocationPermission()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.permissions.collect { state ->
                    when {
                        state.isLocationPermissionGranted == false -> {
                            requestLocationPermissionLauncher
                        }
                        state.isNotificationPermissionGranted == false -> {
                            requestLocationPermissionLauncher
                        }
                        state.isLocationPermissionGranted == true && state.isNotificationPermissionGranted == true -> {
                            startLocationUpdates()
                        }
                        else -> Unit
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