package com.example.instabug_task.ui.forecast

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.instabug_task.R
import com.example.instabug_task.databinding.ForecastItemBinding
import com.example.instabug_task.domain.model.DayWeather
import com.example.instabug_task.ui.utils.getDayOfWeekFromDate

class ForecastAdapter(val days: List<DayWeather>) :
    RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ForecastItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    inner class ViewHolder(val binding: ForecastItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(day: DayWeather) {
            binding.maxMin.text = buildString {
                append("H:")
                append(day.tempmax)
                append("° ")
                append("L:")
                append(day.tempmin)
                append("°")
            }
            val date =
                getDayOfWeekFromDate(day.datetime ?: "") + " ${day.datetime?.substring(5)}"
            binding.date.text = date
            binding.temp.text = buildString {
                append(day.temp)
                append("°")
            }
            binding.conditions.setImageResource(day.icon ?: R.drawable.ic_cloudy)
            binding.condition.text = day.conditions.orEmpty()
        }
    }
}