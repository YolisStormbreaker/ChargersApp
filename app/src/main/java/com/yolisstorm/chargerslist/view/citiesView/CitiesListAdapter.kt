package com.yolisstorm.chargerslist.view.citiesView

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.yolisstorm.chargerslist.view.RecyclerViewItemType
import com.yolisstorm.datasource.models.CityWithCharger
import java.util.UUID

class CitiesListAdapter(
    private val clickListener: CityClickListener
) : ListAdapter<CityNameValueClass, RecyclerView.ViewHolder>(CityDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (RecyclerViewItemType.getByValue(viewType)) {
            RecyclerViewItemType.Item -> CityViewHolder.from(parent)
            RecyclerViewItemType.Header -> throw (IllegalArgumentException("Неправильный holder для Adapter"))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CityViewHolder -> {
                val item = getItem(position)
                holder.bind(clickListener, item)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is CityNameValueClass -> RecyclerViewItemType.Item.type
            else -> RecyclerViewItemType.Header.type
        }
    }

    override fun getItemId(position: Int): Long {
        return currentList[position].hashCode().toLong()
    }
}

class CityClickListener(private val clickListener: (item: CityNameValueClass) -> Unit) {
    fun onClick(item: CityNameValueClass) = clickListener(item)
}

class CityDiffCallback : DiffUtil.ItemCallback<CityNameValueClass>() {
    override fun areItemsTheSame(oldItem: CityNameValueClass, newItem: CityNameValueClass): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: CityNameValueClass, newItem: CityNameValueClass): Boolean {
        return oldItem == newItem
    }
}
