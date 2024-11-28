package com.dicoding.glucoscan.helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.glucoscan.R
import com.dicoding.glucoscan.helper.DailyRoundedAdapter.ViewHolder
import com.dicoding.glucoscan.ui.component.DailyRounded

class RiwayatRoundedAdapter(private val items: List<String>) :
    RecyclerView.Adapter<RiwayatRoundedAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val riwayatRounded: DailyRounded = view.findViewById(R.id.dailyRoundedView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_riwayat_rounded, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.riwayatRounded.data = items[position]
        holder.riwayatRounded.dimension = 28f
    }
}