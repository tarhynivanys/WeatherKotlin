package com.kozin.weatherkotlin.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kozin.weatherkotlin.R
import com.kozin.weatherkotlin.data.response.future.FutureList
import com.kozin.weatherkotlin.data.response.future.FutureWeatherEntry

class RecyclerViewAdapter(private val forecastList: FutureWeatherEntry)
    : RecyclerView.Adapter<MyViewHolder>() {

//    private var forecastList = ArrayList<FutureWeatherEntry>()

//    fun setItems(forecastList: ArrayList<FutureWeatherEntry>){
//        this.forecastList.addAll(forecastList)
//        notifyDataSetChanged()
//    }

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
        return forecastList.list.size
    }


}

class MyViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){

    private val dt_txt: TextView = itemView.findViewById(R.id.tvDateTime)
    private val desc: TextView = itemView.findViewById(R.id.tvDescription)
    private val temp: TextView = itemView.findViewById(R.id.tvTemper)
    private val icon: ImageView = itemView.findViewById(R.id.imgForecastIcon)

    fun bind(forecast: FutureList){
//        binding.tvDateTime.text = forecast.list[0].dt_txt
//        binding.tvDescription.text = forecast.list[0].weather[0].description
//        binding.tvTemper.text = forecast.list[0].main.temp.toString()

        dt_txt.text = forecast.dt_txt
        desc.text = forecast.weather[0].description
        temp.text = forecast.main.temp.toString()
//        Glide.with(requireContext()).load(StringBuilder("http://openweathermap.org/img/wn/")
//            .append(forecast.weather[0].icon).append(".png").toString()).into(icon)

    }

}



//class MoviesAdapter(val movies: List<Result>): RecyclerView.Adapter<MoviesViewHolder>() {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
//        return MoviesViewHolder(view)
//    }
//
//    override fun getItemCount(): Int {
//        return movies.size
//    }
//
//    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
//        return holder.bind(movies[position])
//    }
//}
//
//class MoviesViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
//    private val photo:ImageView = itemView.findViewById(R.id.movie_photo)
//    private val title:TextView = itemView.findViewById(R.id.movie_title)
//    private val overview:TextView = itemView.findViewById(R.id.movie_overview)
//    private val rating:TextView = itemView.findViewById(R.id.movie_rating)
//
//    fun bind(movie: Result) {
//        Glide.with(itemView.context).load("http://image.tmdb.org/t/p/w500${movie.poster_path}").into(photo)
//        title.text = "Title: "+movie.title
//        overview.text = movie.overview
//        rating.text = "Rating : "+movie.vote_average.toString()
//    }
//
//}
