package com.yolisstorm.chargerslist.view.chargersView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.yolisstorm.chargerslist.R
import com.yolisstorm.chargerslist.databinding.ChargerItemViewHolderBinding
import com.yolisstorm.datasource.models.ChargerDto

class ChargerItemViewHolder private constructor(
    private val binding: ChargerItemViewHolderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ChargerDto) {
        with(binding) {
            chargerName.text = item.name
            chargerAddress.text = item.address
            chargerState.setBackgroundColor(
                ResourcesCompat.getColor(
                    binding.root.resources,
                    if (item.isBusy) {
                        R.color.busy_color
                    } else {
                        R.color.available_color
                    },
                    binding.root.context.theme
                )
            )
        }
    }

    companion object {
        fun from(parent: ViewGroup): ChargerItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ChargerItemViewHolderBinding.inflate(layoutInflater, parent, false)
            return ChargerItemViewHolder(binding)
        }
    }
}