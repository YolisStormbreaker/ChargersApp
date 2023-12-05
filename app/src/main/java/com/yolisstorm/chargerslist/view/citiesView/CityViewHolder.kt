package com.yolisstorm.chargerslist.view.citiesView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yolisstorm.chargerslist.databinding.CityItemViewHolderBinding
import com.yolisstorm.datasource.models.CityWithCharger

class CityViewHolder private constructor(val binding: CityItemViewHolderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(clickListener: CityClickListener, item: CityNameValueClass?) {
        if (item != null) {
            binding.cityNameHolder.text = item.cityName
            binding.cityContainer.setOnClickListener {
                clickListener.onClick(item)
            }
        }
    }

    companion object {
        fun from(parent: ViewGroup): CityViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = CityItemViewHolderBinding.inflate(layoutInflater, parent, false)
            return CityViewHolder(binding)
        }
    }
}