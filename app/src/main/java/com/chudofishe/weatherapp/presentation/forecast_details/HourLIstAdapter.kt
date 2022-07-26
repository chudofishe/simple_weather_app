package com.chudofishe.weatherapp.presentation.forecast_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chudofishe.weatherapp.R
import com.chudofishe.weatherapp.databinding.HourItemBinding
import com.chudofishe.weatherapp.domain.model.HourForecast

class HourLIstAdapter(private val hourList: List<HourForecast>) : RecyclerView.Adapter<HourLIstAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HourItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(hourList[position])
    }

    override fun getItemCount(): Int = hourList.size

    inner class ViewHolder(private val binding: HourItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HourForecast) {
            binding.apply {
                temp.text = itemView.resources.getString(R.string.temperature, item.temp)
                time.text = item.time
                Glide.with(itemView).load(item.icon).into(icon)
            }
        }
    }
}