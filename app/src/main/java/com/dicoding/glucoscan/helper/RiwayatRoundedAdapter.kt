package com.dicoding.glucoscan.helper

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.glucoscan.R
import com.dicoding.glucoscan.databinding.ItemDailyRoundedBinding
import com.dicoding.glucoscan.helper.DailyRoundedAdapter.ViewHolder
import com.dicoding.glucoscan.ui.component.DailyRounded

class RiwayatRoundedAdapter(
    private val items: List<String>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<RiwayatRoundedAdapter.ViewHolder>() {

        private var selectedPosition = 0

    class ViewHolder(private val binding: ItemDailyRoundedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String, isSelected: Boolean) {
            val data = changeFormatTimestamp(item, "dd", "yearMonthDate")
            binding.dailyRoundedView.data = data
            binding.dailyRoundedView.dimension = 28f
            if (isSelected) {
                binding.dailyRoundedView.color = binding.root.context.getColor(R.color.white)
                binding.dailyRoundedView.customBackgroundColor = binding.root.context.getColor(R.color.blue_500)
            } else {
                binding.dailyRoundedView.color = binding.root.context.getColor(R.color.neutral_900)
                binding.dailyRoundedView.customBackgroundColor = binding.root.context.getColor(R.color.neutral_300)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDailyRoundedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position == selectedPosition)
        holder.itemView.setOnClickListener {
            val previousSelectedPosition = selectedPosition
            selectedPosition = holder.adapterPosition
            notifyItemChanged(previousSelectedPosition)
            notifyItemChanged(selectedPosition)

            onItemClick(items[position])
        }
    }
}