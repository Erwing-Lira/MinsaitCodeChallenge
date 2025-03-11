package com.example.profile.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil3.load
import coil3.request.error
import coil3.request.placeholder
import com.example.profile.R
import com.example.profile.databinding.FragmentProfileBinding
import com.example.profile.ui.state.UIState
import com.example.profile.ui.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    private fun initData() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is UIState.Error -> {
                            binding.circularPopularProgress.isVisible = true
                            binding.countryContainer.isVisible = false
                            binding.nameContainer.isVisible = false
                            binding.usernameContainer.isVisible = false
                            binding.adultContainer.isVisible = false
                        }
                        is UIState.Loading -> {
                            binding.circularPopularProgress.isVisible = true
                            binding.countryContainer.isVisible = false
                            binding.nameContainer.isVisible = false
                            binding.usernameContainer.isVisible = false
                            binding.adultContainer.isVisible = false
                        }
                        is UIState.Success -> {
                            with(binding) {
                                circularPopularProgress.isVisible = false
                                val urlImage = "https://image.tmdb.org/t/p/w500${state.profile.avatar_path}"
                                avatar.load(urlImage) {
                                    placeholder(R.drawable.ic_user)
                                    error(R.drawable.ic_user)
                                }
                                if (state.profile.name.isEmpty()) {
                                    nameValue.text = resources.getString(R.string.no_name)
                                } else {
                                    nameValue.text = state.profile.name
                                }
                                usernameValue.text = state.profile.username
                                countryValue.text = "${state.profile.iso_639_1}-${state.profile.iso_3166_1}"
                                adultSwitch.isChecked = state.profile.include_adult

                                binding.countryContainer.isVisible = true
                                binding.nameContainer.isVisible = true
                                binding.usernameContainer.isVisible = true
                                binding.adultContainer.isVisible = true
                            }
                        }
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