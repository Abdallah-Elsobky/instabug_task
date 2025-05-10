package com.example.instabug_task.ui.home

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.instabug_task.R
import com.example.instabug_task.databinding.DayForecastItemBinding
import com.example.instabug_task.domain.model.DayWeather
import com.example.instabug_task.ui.utils.getDayOfWeekFromDate

class FiveForecastAdapter(val days: List<DayWeather>) :
    RecyclerView.Adapter<FiveForecastAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            DayForecastItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val day = days[position]
        holder.bind(day)
    }

    override fun getItemCount() = days.size

    inner class ViewHolder(val binding: DayForecastItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(day: DayWeather) {
            binding.maxMinDegree.text = buildString {
                append(day.tempmax)
                append("° / ")
                append(day.tempmin)
                append("°")
            }
            binding.date.text = getDayOfWeekFromDate(day.datetime ?: "")
            binding.dayState.setImageResource(day.icon ?: R.drawable.ic_cloudy)
        }
    }
}