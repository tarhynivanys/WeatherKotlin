package com.kozin.weatherkotlin.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kozin.weatherkotlin.R
import com.kozin.weatherkotlin.data.response.future.FutureList
import com.kozin.weatherkotlin.data.response.future.FutureWeatherEntry
import com.kozin.weatherkotlin.ui.fragments.FutureWeatherFragment
import com.kozin.weatherkotlin.utils.SessionManager
import kotlinx.android.synthetic.main.fragment_future_weather.view.*
import java.util.*

class RecyclerViewAdapter(
    private val forecastList: FutureWeatherEntry
) : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val dt_txt: TextView = itemView.findViewById(R.id.tvDateTime)
        private val desc: TextView = itemView.findViewById(R.id.tvDescription)
        private val temp: TextView = itemView.findViewById(R.id.tvTemper)
        private val icon: ImageView = itemView.findViewById(R.id.imgForecastIcon)

        var string: String = ""

        init {
            itemView.setOnClickListener { v: View ->
                val position: Int = adapterPosition

                Log.i("SAG", position.toString())

                //val date = Date()
                val cal = Calendar.getInstance()
                //cal.time = date

                cal.add(Calendar.MONTH, 1)

                string = "${cal.get(Calendar.YEAR)}" +
                        "-${cal.get(Calendar.MONTH)}" +
                        "-0${cal.get(Calendar.DAY_OF_MONTH)}"

                itemView.findNavController()
                    .navigate(R.id.action_futureWeatherFragment_to_futureDetailWeatherFragment,
                        bundleOf("key" to forecastList.list[0].dt_txt))

//                when (position) {
//                    0 ->
//                }
            }
        }

        fun parse(date: String){
            //val date = Date()
            val cal = Calendar.getInstance()
            //cal.time = date

            cal.add(Calendar.MONTH, 1)

            val qwe: String = "${cal.get(Calendar.YEAR)}" +
                    "-${cal.get(Calendar.MONTH)}" +
                    "-0${cal.get(Calendar.DAY_OF_MONTH)}"

        }

        fun bind(forecast: FutureList){
            dt_txt.text = forecast.dt_txt
            desc.text = forecast.weather[0].description
            temp.text = forecast.main.temp.toString()
            Glide.with(this.icon).load(
                StringBuilder("http://openweathermap.org/img/wn/")
                    .append(forecast.weather[0].icon).append(".png").toString()
            ).into(icon)
        }

/*
        fun parse(forecast: FutureList) {
            dt_txt.text = forecast.dt_txt

            val date = Date()
            //val cal = Calendar.getInstance()
            //cal.time = date

            var jop: String = date.toString().substringBefore(' ')

        when (jop.length == 3){
            jop.endsWith("Mon", ignoreCase = false) ->
                jop += "day"

            jop.endsWith("Tue", ignoreCase = false) ->
                jop += "sday"

            jop.endsWith("Wed", ignoreCase = false) ->
                jop += "nesday"

            jop.endsWith("Thu", ignoreCase = false) ->
                jop += "rsday"

            jop.endsWith("Fri", ignoreCase = false) ->
                jop += "day"

            jop.endsWith("Sat", ignoreCase = false) ->
                jop += "urday"

            jop.endsWith("Sun", ignoreCase = false) ->
                jop += "day"
        }

            desc.text = jop

            temp.text = forecast.main.temp.toString()
            Glide.with(this.icon).load(
                StringBuilder("http://openweathermap.org/img/wn/")
                    .append(forecast.weather[0].icon).append(".png").toString()
            ).into(icon)
        }
*/

/*
        fun bind(forecast: FutureList) {
            binding.tvDateTime.text = forecast.list[0].dt_txt
            binding.tvDescription.text = forecast.list[0].weather[0].description
            binding.tvTemper.text = forecast.list[0].main.temp.toString()

            val date = Date()
            val cal = Calendar.getInstance()
            cal.time = date

            val asd: String = "${cal.get(Calendar.HOUR)}" + " " + "${cal.get(Calendar.HOUR_OF_DAY)}"


            cal.add(Calendar.MONTH, 1)

            val qwe: String = "${cal.get(Calendar.YEAR)}" +
                    "-${cal.get(Calendar.MONTH)}" +
                    "-0${cal.get(Calendar.DAY_OF_MONTH)}"

            val zxc: String = "${cal.get(Calendar.YEAR)}" +
                    "-${cal.get(Calendar.MONTH)}" +
                    "-0${cal.get(Calendar.DAY_OF_MONTH) + 1}"

            when {
                forecast.dt_txt.contains(qwe) ->
                    parse(forecast)
                forecast.dt_txt.contains(zxc) ->
                    parse(forecast)
            }

            if (forecast.dt_txt.contains(qwe)) {
                dt_txt.text = forecast.dt_txt
                desc.text = forecast.weather[0].description
                temp.text = forecast.main.temp.toString()
                Glide.with(this.icon).load(
                    StringBuilder("http://openweathermap.org/img/wn/")
                        .append(forecast.weather[0].icon).append(".png").toString()
                ).into(icon)
            }

        }
*/

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_row, parent, false)

//        val binding = CustomRowBinding.inflate(layoutInflater, parent, false)

        return MyViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        return holder.bind(forecastList.list[position])
    }

    override fun getItemCount(): Int {
        //return forecastList.list.size
        return 5
    }


}

/*
class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val dt_txt: TextView = itemView.findViewById(R.id.tvDateTime)
    private val desc: TextView = itemView.findViewById(R.id.tvDescription)
    private val temp: TextView = itemView.findViewById(R.id.tvTemper)
    private val icon: ImageView = itemView.findViewById(R.id.imgForecastIcon)

    fun parse(forecast: FutureList) {
        dt_txt.text = forecast.dt_txt

        val date = Date()
        //val cal = Calendar.getInstance()
        //cal.time = date

        var jop: String = date.toString().substringBefore(' ')

//        when (jop.length == 3){
//            jop.endsWith("Mon", ignoreCase = false) ->
//                jop += "day"
//
//            jop.endsWith("Tue", ignoreCase = false) ->
//                jop += "sday"
//
//            jop.endsWith("Wed", ignoreCase = false) ->
//                jop += "nesday"
//
//            jop.endsWith("Thu", ignoreCase = false) ->
//                jop += "rsday"
//
//            jop.endsWith("Fri", ignoreCase = false) ->
//                jop += "day"
//
//            jop.endsWith("Sat", ignoreCase = false) ->
//                jop += "urday"
//
//            jop.endsWith("Sun", ignoreCase = false) ->
//                jop += "day"
//        }

        desc.text = jop

        temp.text = forecast.main.temp.toString()
        Glide.with(this.icon).load(
            StringBuilder("http://openweathermap.org/img/wn/")
                .append(forecast.weather[0].icon).append(".png").toString()
        ).into(icon)
    }

    fun bind(forecast: FutureList) {
//        binding.tvDateTime.text = forecast.list[0].dt_txt
//        binding.tvDescription.text = forecast.list[0].weather[0].description
//        binding.tvTemper.text = forecast.list[0].main.temp.toString()

        val date = Date()
        val cal = Calendar.getInstance()
        cal.time = date

        val asd: String = "${cal.get(Calendar.HOUR)}" + " " + "${cal.get(Calendar.HOUR_OF_DAY)}"


        cal.add(Calendar.MONTH, 1)

        val qwe: String = "${cal.get(Calendar.YEAR)}" +
                "-${cal.get(Calendar.MONTH)}" +
                "-0${cal.get(Calendar.DAY_OF_MONTH)}"

        val zxc: String = "${cal.get(Calendar.YEAR)}" +
                "-${cal.get(Calendar.MONTH)}" +
                "-0${cal.get(Calendar.DAY_OF_MONTH) + 1}"

        //Log.i("TAG", asd)

        when {
            forecast.dt_txt.contains(qwe) ->
                parse(forecast)
            forecast.dt_txt.contains(zxc) ->
                parse(forecast)
        }

//        if (forecast.dt_txt.contains(qwe)){
//            dt_txt.text = forecast.dt_txt
//            desc.text = forecast.weather[0].description
//            temp.text = forecast.main.temp.toString()
//            Glide.with(this.icon).load(StringBuilder("http://openweathermap.org/img/wn/")
//                .append(forecast.weather[0].icon).append(".png").toString()).into(icon)
//        }

    }

}
*/



