package com.dicoding.glucoscan.helper

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.glucoscan.R
import com.dicoding.glucoscan.ui.component.DailyRounded

class DailyRoundedAdapter(private val items: List<List<String>>) :
    RecyclerView.Adapter<DailyRoundedAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dailyRounded: DailyRounded = view.findViewById(R.id.dailyRoundedView)
        val day: TextView = view.findViewById(R.id.day)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_daily_rounded, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dailyRounded
        holder.dailyRounded.data = items[position].last()
        val newDate = changeFormatTimestamp(items[position].first(), "EEE", "yearMonthDate")
        holder.day.text = newDate
        holder.dailyRounded.dimension = 28f
    }

    override fun getItemCount() = items.size
}