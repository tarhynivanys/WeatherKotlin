package com.kozin.weatherkotlin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kozin.weatherkotlin.data.response.future.InfoDay
import com.kozin.weatherkotlin.databinding.ItemWeatherHourOfDayBinding

class DetailWeatherAdapter(private val list: List<InfoDay>?):
    RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemWeatherHourOfDayBinding = ItemWeatherHourOfDayBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(list!![position])

    override fun getItemCount(): Int {
        return list?.size!!
    }
}

class ViewHolder(private val itemBinding: ItemWeatherHourOfDayBinding): RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(infoDay: InfoDay) {
        itemBinding.textViewHourOfDay.text = infoDay.getHourOfDay()
        itemBinding.textViewTemp.text = infoDay.main?.getTempString()
    }
}