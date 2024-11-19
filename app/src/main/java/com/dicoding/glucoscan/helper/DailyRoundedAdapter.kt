package com.dicoding.glucoscan.helper

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.glucoscan.R
import com.dicoding.glucoscan.ui.component.DailyRounded

class DailyRoundedAdapter(private val items: List<String>) :
    RecyclerView.Adapter<DailyRoundedAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dailyRounded: DailyRounded = view.findViewById(R.id.dailyRoundedView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_daily_rounded, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dailyRounded.data = items[position]
        holder.dailyRounded.dimension = 10f
    }

    override fun getItemCount() = items.size
}