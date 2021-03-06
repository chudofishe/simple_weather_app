package com.chudofishe.weatherapp.presentation.forecast_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chudofishe.weatherapp.R
import com.chudofishe.weatherapp.databinding.ForecastItemBinding
import com.chudofishe.weatherapp.domain.model.Forecast
import com.chudofishe.weatherapp.domain.model.ForecastDetails
import java.time.LocalDate

class ForecastListAdapter(private val values: List<Forecast>,
    private val clickListener: (item: ForecastDetails) -> Unit) : RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = ForecastItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(private val binding: ForecastItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Forecast) {
            binding.apply {
                weekDay.text = item.date.dayOfWeek.toString()
                tempAndCondition.text = itemView.resources.getString(R.string.temp_and_condition, item.avgTemp.toInt(), item.text)
                Glide.with(itemView).load(item.icon).into(conditionIcon)
            }

            itemView.setOnClickListener {
                clickListener(item.details)
            }
        }
    }

}
