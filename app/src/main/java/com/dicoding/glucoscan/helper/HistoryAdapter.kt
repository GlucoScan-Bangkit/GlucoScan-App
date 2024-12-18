package com.dicoding.glucoscan.helper

import android.icu.text.DecimalFormat
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.glucoscan.data.response.DataItem
import com.dicoding.glucoscan.databinding.ItemRiwayatActivityBinding

class HistoryAdapter(private val items: List<DataItem?>?) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ItemRiwayatActivityBinding) : RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.N)
        fun bind(item: DataItem?) {
            Glide.with(binding.root.context)
                .load(item)
                .into(binding.ivImage)
            val time = changeFormatTimestamp(item?.timestamp!!, "HH:mm")
            var sugarDouble: Double = 0.0
            val spoonContent = if (!item.kandunganGula.isNullOrEmpty()) {
                sugarDouble = item.kandunganGula[0].toString().toDoubleOrNull() ?: 0.0
                (sugarDouble / 12)
            } else {
                0.0
            }
            val formatted = DecimalFormat("#.##")
            val spoonContentFormatted = formatted.format(spoonContent)
            binding.tvTime.text = "$time WIB"
            binding.tvSugar.text = "$sugarDouble gr"
            binding.tvSpoon.text = "$spoonContentFormatted Sdm"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRiwayatActivityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items!![position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items?.size ?: 0
}