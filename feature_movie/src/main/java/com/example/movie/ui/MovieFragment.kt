package com.example.movie.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.movie.R
import com.example.movie.databinding.FragmentMovieBinding
import com.example.movie.ui.adapter.MovieAdapter
import com.example.movie.ui.state.MovieState
import com.example.movie.ui.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.fragment_movie) {
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieViewModel by viewModels()

    private lateinit var moviePopularAdapter: MovieAdapter
    private lateinit var movieTopAdapter: MovieAdapter
    private lateinit var movieRecommendationAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initElements()
        observeState()
    }

    private fun initElements() {
        moviePopularAdapter = MovieAdapter(::onMovieClicked)
        binding.recyclerPopularMovies.adapter = moviePopularAdapter
        movieTopAdapter = MovieAdapter(::onMovieClicked)
        binding.recyclerTopMovies.adapter = movieTopAdapter
        movieRecommendationAdapter = MovieAdapter()
        binding.recyclerRecommendedMovies.adapter = movieRecommendationAdapter
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.isConnected.collect { isConnected ->
                    binding.containerRecommended.isVisible = isConnected
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.popularState.collect { state ->
                    when (state) {
                        is MovieState.Error -> {
                            binding.errorPopular.text = state.message
                            binding.errorPopular.isVisible = true
                            binding.circularPopularProgress.isVisible = false
                        }

                        MovieState.Loading -> {
                            binding.circularPopularProgress.isVisible = true
                        }

                        is MovieState.Success -> {
                            moviePopularAdapter.submitList(state.movies)
                            binding.recyclerPopularMovies.adapter = moviePopularAdapter
                            binding.recyclerPopularMovies.isVisible = true
                            binding.circularPopularProgress.isVisible = false
                        }
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.bestState.collect { state ->
                    when (state) {
                        is MovieState.Error -> {
                            binding.errorTop.text = state.message
                            binding.errorTop.isVisible = true
                            binding.circularTopProgress.isVisible = false
                        }

                        MovieState.Loading -> {
                            binding.circularTopProgress.isVisible = true
                        }

                        is MovieState.Success -> {
                            movieTopAdapter.submitList(state.movies)
                            binding.recyclerTopMovies.isVisible = true
                            binding.circularTopProgress.isVisible = false
                        }
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.recommendedState.collect { state ->
                    when (state) {
                        is MovieState.Error -> {
                            binding.errorRecommended.text = state.message
                            binding.errorRecommended.isVisible = true
                            binding.circularRecommendedProgress.isVisible = false
                        }
                        MovieState.Loading -> {
                            binding.circularRecommendedProgress.isVisible = true
                        }
                        is MovieState.Success -> {
                            movieRecommendationAdapter.submitList(state.movies)
                            binding.recyclerRecommendedMovies.adapter = movieRecommendationAdapter
                            binding.recyclerRecommendedMovies.isVisible = true
                            binding.circularRecommendedProgress.isVisible = false
                        }
                    }
                }
            }
        }
    }

    fun onMovieClicked(id: Int) {
        Toast.makeText(
            requireContext(),
            "Movie clocked $id",
            Toast.LENGTH_SHORT
        ).show()
        viewModel.getRecommendedMovies(id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}