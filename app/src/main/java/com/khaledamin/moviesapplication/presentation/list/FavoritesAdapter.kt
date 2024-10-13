package com.khaledamin.moviesapplication.presentation.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.khaledamin.moviesapplication.R
import com.khaledamin.moviesapplication.databinding.ItemMovieBinding
import com.khaledamin.moviesapplication.domain.model.Movie
import com.khaledamin.moviesapplication.presentation.callbacks.MovieCallback
import com.khaledamin.moviesapplication.presentation.callbacks.MovieFavoriteButtonCallback

class FavoritesAdapter(
    val context: Context,
    val oldList: ArrayList<Movie>,
    val callback: MovieCallback,
    val buttonCallback: MovieFavoriteButtonCallback,
) : Adapter<FavoritesAdapter.MoviesListViewHolder>() {

    inner class MoviesListViewHolder(val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                callback.onMovieClicked(oldList[layoutPosition])
            }
            binding.favoriteBtn.setOnClickListener {
                buttonCallback.onFavoriteButtonClicked(
                    oldList[layoutPosition],
                    !oldList[layoutPosition].isFavorite
                )
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesListViewHolder {
        return MoviesListViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_movie,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = oldList.size

    override fun onBindViewHolder(holder: MoviesListViewHolder, position: Int) {
        holder.binding.movie = oldList[position]
    }

    fun updateData(newList: ArrayList<Movie>) {
        val moviesUtil = DiffUtil.calculateDiff(MoviesUtil(oldList, newList))
        oldList.clear()
        oldList.addAll(newList)
        moviesUtil.dispatchUpdatesTo(this)
    }
}