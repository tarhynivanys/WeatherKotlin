package com.kozin.weatherkotlin.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kozin.weatherkotlin.R
import com.kozin.weatherkotlin.data.response.future.InfoDay
import com.kozin.weatherkotlin.data.response.future.FutureWeatherEntry
import com.kozin.weatherkotlin.databinding.CustomRowBinding
import java.util.*

class ForecastAdapter(
    private val forecastList: List<InfoDay>?,
    private val listener: WeatherItemListener,
) : RecyclerView.Adapter<MyViewHolder>() {

    interface WeatherItemListener {
        fun onClickedWeather(currentDay: InfoDay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: CustomRowBinding =
            CustomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        return holder.bind(forecastList!![position])
    }

    override fun getItemCount(): Int {
        return forecastList!!.size
    }


}

class MyViewHolder(
    private val itemBinding: CustomRowBinding,
    private val listener: ForecastAdapter.WeatherItemListener
) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    init {
        itemBinding.root.setOnClickListener(this)
    }

    private lateinit var currentDay: InfoDay

    fun bind(item: InfoDay) {
        this.currentDay = item
        itemBinding.tvDateTime.text = item.getDay()
        itemBinding.tvTemper.text = item.getDayDate()
        //itemBinding.tvTempMin.text = item.main?.getTempMinString()
        //itemBinding.tvTempMax.text = item.main?.getTempMaxString()
        itemBinding.tvDescription.text = item.weather?.get(0)?.description
        Glide.with(itemBinding.imgForecastIcon)
            .load(item.getWeatherItemValue())
            .into(itemBinding.imgForecastIcon)
    }

    override fun onClick(p0: View?) {
        listener.onClickedWeather(currentDay)

    }


}




