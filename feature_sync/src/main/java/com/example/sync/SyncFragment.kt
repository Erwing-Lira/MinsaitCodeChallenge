package com.example.sync

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.sync.databinding.FragmentSyncBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class SyncFragment : Fragment(R.layout.fragment_sync) {
    private var _binding: FragmentSyncBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSyncBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private val multiplePermissionsContract = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissionsStatusMap ->
        if (permissionsStatusMap.all { it.value }) {
            startLocationUpdates()
        } else {
            Toast.makeText(requireContext(), "Permiso de ubicación necesario", Toast.LENGTH_SHORT).show()
        }
    }

    private fun requestLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                startLocationUpdates()
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                Toast.makeText(requireActivity(), "Permiso necesario", Toast.LENGTH_SHORT).show()
            }
            else -> {
                val permissions = when {
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                            Manifest.permission.POST_NOTIFICATIONS
                        )
                    }
                    else -> {
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    }
                }
                multiplePermissionsContract.launch(permissions)
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
        requestLocationPermission()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}