package com.khaledamin.moviesapplication.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.khaledamin.moviesapplication.R
import com.khaledamin.moviesapplication.databinding.ItemTabBinding
import com.khaledamin.moviesapplication.presentation.model.Tab

class TabsAdapter(private val data: ArrayList<Tab>,
                  private val selectTab: (Tab) -> Unit) :
    Adapter<TabsAdapter.TabsViewHolder>() {

    private var selectedTabPosition = 0

    inner class TabsViewHolder(val binding: ItemTabBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            data[selectedTabPosition].isSelected = true
            binding.root.setOnClickListener {
                val position = layoutPosition
                if (selectedTabPosition != position) {
                    data.forEachIndexed { index, tab ->
                        tab.isSelected = index == position
                    }
                }
                notifyItemChanged(selectedTabPosition)
                notifyItemChanged(position)
                selectedTabPosition = position
                selectTab.invoke(data[layoutPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabsAdapter.TabsViewHolder {
        return TabsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_tab,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TabsAdapter.TabsViewHolder, position: Int) {
        holder.binding.tab = data[position]
    }

    override fun getItemCount(): Int = data.size
}