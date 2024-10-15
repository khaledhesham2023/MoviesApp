package com.khaledamin.moviesapplication.presentation.list

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.khaledamin.moviesapplication.R
import com.khaledamin.moviesapplication.databinding.ItemMovieBinding
import com.khaledamin.moviesapplication.domain.model.Movie
import com.khaledamin.moviesapplication.presentation.callbacks.MovieFavoriteButtonCallback

class MoviesAdapter(
    val callback: (Movie) -> Unit,
    val buttonCallback: MovieFavoriteButtonCallback,
) : PagingDataAdapter<Movie, MoviesAdapter.MoviesViewHolder>(COMPARATOR) {
    inner class MoviesViewHolder(val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                callback.invoke(binding.movie!!)
            }
            binding.favoriteBtn.setOnClickListener {
                getItem(layoutPosition)!!.isFavorite = !getItem(layoutPosition)!!.isFavorite
                notifyItemChanged(layoutPosition)
                buttonCallback.onFavoriteButtonClicked(getItem(layoutPosition)!!, getItem(layoutPosition)!!.isFavorite)
            }
        }
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = getItem(position)
        holder.binding.movie = movie
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_movie,
                parent,
                false
            )
        )
    }

    companion object {
        object COMPARATOR : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

        }
    }
}