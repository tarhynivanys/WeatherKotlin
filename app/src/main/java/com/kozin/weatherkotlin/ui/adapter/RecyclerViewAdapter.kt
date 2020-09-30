package com.kozin.weatherkotlin.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kozin.weatherkotlin.R
import com.kozin.weatherkotlin.data.response.future.FutureWeatherEntry
import kotlinx.android.synthetic.main.custom_row.view.*

class RecyclerViewAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var forecast = emptyList<FutureWeatherEntry>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_row, parent, false))
    }

    override fun getItemCount(): Int {
        return forecast.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = forecast[position]

        holder.itemView.tvDateTime.text = currentItem.list[position].dt_txt
        holder.itemView.tvTemper.text = currentItem.list[position].main.temp.toString()
        holder.itemView.tvDescription.text = currentItem.list[position].weather[0].description

        Glide.with(requireContext()).load(StringBuilder("http://openweathermap.org/img/wn/")
            .append(currentWeather.data.weather[0].icon).append(".png").toString()).into(imgIcon)

        holder.itemView.rowLayout.setOnClickListener{
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }

    }

    fun setData(forecast: List<FutureWeatherEntry>){
        this.forecast = forecast
        notifyDataSetChanged()
    }


}