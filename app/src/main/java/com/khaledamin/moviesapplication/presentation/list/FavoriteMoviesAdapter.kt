package com.khaledamin.moviesapplication.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.khaledamin.moviesapplication.R
import com.khaledamin.moviesapplication.databinding.ItemMovieBinding
import com.khaledamin.moviesapplication.domain.model.Movie

class FavoriteMoviesAdapter(
    val oldList: ArrayList<Movie>,
    val navigator: (Movie) -> Unit,
    val removeFromFavorite: (Movie,Boolean) -> Unit,
) : Adapter<FavoriteMoviesAdapter.MoviesListViewHolder>() {

    inner class MoviesListViewHolder(val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                navigator.invoke(oldList[layoutPosition])
            }
            binding.favoriteBtn.setOnClickListener {
                removeFromFavorite.invoke(oldList[layoutPosition],!oldList[layoutPosition].isFavorite)
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