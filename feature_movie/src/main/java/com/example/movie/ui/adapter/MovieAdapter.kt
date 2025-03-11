package com.example.movie.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import com.example.movie.R
import com.example.movie.databinding.MovieItemBinding
import com.example.network.data.remote.model.MovieDTO

class MovieAdapter(
    private val onMovieClick: (Int) -> Unit = {},
): ListAdapter<MovieDTO, MovieAdapter.MovieViewHolder>(MovieDiffCallback()) {
    private class MovieDiffCallback : DiffUtil.ItemCallback<MovieDTO>() {
        override fun areItemsTheSame(oldItem: MovieDTO, newItem: MovieDTO): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MovieDTO, newItem: MovieDTO): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
        holder.itemView.setOnClickListener {
            onMovieClick(movie.id)
        }
    }

    inner class MovieViewHolder(
        private val binding: MovieItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieDTO) {
            val imageUrl = "https://image.tmdb.org/t/p/w500${movie.poster_path}"
            binding.image.load(imageUrl) {
                placeholder(R.drawable.ic_image_search)
                crossfade(true)
                error(R.drawable.ic_error_image)
            }
            binding.title.text = movie.original_title
            binding.date.text = movie.release_date
        }
    }
}