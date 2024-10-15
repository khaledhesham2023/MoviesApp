package com.khaledamin.moviesapplication.presentation.list

import androidx.recyclerview.widget.DiffUtil
import com.khaledamin.moviesapplication.domain.model.Movie


class MoviesUtil(private val oldList: ArrayList<Movie>, private val newList: ArrayList<Movie>) :
    DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}