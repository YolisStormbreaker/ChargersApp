package com.yolisstorm.chargerslist.view.chargersView

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.yolisstorm.chargerslist.view.RecyclerViewItemType
import com.yolisstorm.datasource.models.ChargerDto
import kotlin.random.Random

class ChargerListAdapter : ListAdapter<ChargerDto, RecyclerView.ViewHolder>(ChargerDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (RecyclerViewItemType.getByValue(viewType)) {
            RecyclerViewItemType.Item -> ChargerItemViewHolder.from(parent)
            RecyclerViewItemType.Header -> throw (IllegalArgumentException("Неправильный holder для Adapter"))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ChargerItemViewHolder -> {
                val item = getItem(position)
                holder.bind(item)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ChargerDto -> RecyclerViewItemType.Item.type
            else -> RecyclerViewItemType.Header.type
        }
    }

    override fun getItemId(position: Int): Long {
        return currentList[position].id.hashCode().toLong()
    }
}

class ChargerDiffCallback : DiffUtil.ItemCallback<ChargerDto>() {
    override fun areItemsTheSame(oldItem: ChargerDto, newItem: ChargerDto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ChargerDto, newItem: ChargerDto): Boolean {
        return oldItem == newItem
    }
}
