package com.kozin.weatherkotlin.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kozin.weatherkotlin.R
import com.kozin.weatherkotlin.data.response.future.FutureList
import com.kozin.weatherkotlin.data.response.future.FutureWeatherEntry
import kotlinx.android.synthetic.main.custom_row.view.*
import java.util.*

class DetailWeatherAdapter(private val forecastList: FutureWeatherEntry, private val dateTime: String?)
    : RecyclerView.Adapter<DetailWeatherAdapter.ViewHolder>() {

    inner class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){

        private val dt_txt: TextView = itemView.findViewById(R.id.tvDateTime)
        private val desc: TextView = itemView.findViewById(R.id.tvDescription)
        private val temp: TextView = itemView.findViewById(R.id.tvTemper)
        private val icon: ImageView = itemView.findViewById(R.id.imgForecastIcon)

        fun bind(forecast: FutureList){
//        binding.tvDateTime.text = forecast.list[0].dt_txt
//        binding.tvDescription.text = forecast.list[0].weather[0].description
//        binding.tvTemper.text = forecast.list[0].main.temp.toString()

            val date = Date()
            val cal = Calendar.getInstance()
            cal.time = date

            cal.add(Calendar.MONTH, 1)

            val qwe: String = "${cal.get(Calendar.YEAR)}" +
                    "-${cal.get(Calendar.MONTH)}" +
                    "-0${cal.get(Calendar.DAY_OF_MONTH)}"

            Log.i("TAG", qwe + " " + dateTime)

            if (dateTime?.contains(qwe)!!){
                dt_txt.text = forecast.dt_txt
                desc.text = forecast.weather[0].description
                temp.text = forecast.main.temp.toString()
                Glide.with(this.icon).load(StringBuilder("http://openweathermap.org/img/wn/")
                    .append(forecast.weather[0].icon).append(".png").toString()).into(icon)
            }

//        dt_txt.text = forecast.dt_txt
//        desc.text = forecast.weather[0].description
//        temp.text = forecast.main.temp.toString()
//        Glide.with(this.icon).load(StringBuilder("http://openweathermap.org/img/wn/")
//            .append(forecast.weather[0].icon).append(".png").toString()).into(icon)

        }



}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_row, parent, false)

//        val binding = CustomRowBinding.inflate(layoutInflater, parent, false)

        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(forecastList.list[position])
    }

    override fun getItemCount(): Int {
        //return forecastList.list.size
        return 5
    }


}



