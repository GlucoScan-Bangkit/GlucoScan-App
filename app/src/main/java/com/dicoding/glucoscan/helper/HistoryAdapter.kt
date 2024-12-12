package com.dicoding.glucoscan.helper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.glucoscan.data.response.DataItem
import com.dicoding.glucoscan.databinding.ItemRiwayatActivityBinding

class HistoryAdapter(private val items: List<DataItem?>?) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ItemRiwayatActivityBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DataItem?) {
            Glide.with(binding.root.context)
                .load(item)
                .into(binding.ivImage)
            val time = changeFormatTimestamp(item?.timestamp!!, "HH:mm")
            val spoonContent = if (!item.kandunganGula.isNullOrEmpty()) {
                val sugarDouble = item.kandunganGula[0].toString().toDoubleOrNull() ?: 0.0
                (sugarDouble / 12).toString()
            } else {
                "0"
            }
            binding.tvTime.text = "$time WIB"
            binding.tvSugar.text = "${item.kandunganGula?.get(0)} gr"
            binding.tvSpoon.text = "$spoonContent Sdm"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRiwayatActivityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items!![position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items?.size ?: 0
}