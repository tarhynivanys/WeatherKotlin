package com.kozin.weatherkotlin.data.remote.retrofit

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.kozin.weatherkotlin.data.response.current.CurrentWeatherEntry
import com.kozin.weatherkotlin.data.response.future.FutureWeatherEntry
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "23263760646a6379d75da90c3eabd0c3"
const val BASE_URL = "http://api.openweathermap.org/data/2.5/"

interface ApiInterface {

    @GET(value = "weather")
    suspend fun getCurrentWeatherByCityName(
        @Query("q") location: String,
        @Query("lang") languageCode: String = "en",
        @Query("units") units: String = "metric"
    ): Response<CurrentWeatherEntry>

    @GET(value = "forecast")
    suspend fun getFutureWeatherByCityName(
        @Query("q") location: String,
        @Query("lang") languageCode: String = "en",
        @Query("units") units: String = "metric"
    ): Response<FutureWeatherEntry>

    @GET(value = "weather")
    suspend fun getCurrentWeatherByLatLng(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("lang") languageCode: String = "en",
        @Query("units") units: String = "metric"
    ): Response<CurrentWeatherEntry>

    companion object {
        operator fun invoke(): ApiInterface {
            val requestInterceptor = Interceptor {
                val url = it.request()
                    .url
                    .newBuilder()
                    .addQueryParameter("appid", API_KEY)
                    .build()
                val request = it.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor it.proceed(request)
            }
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface::class.java)
        }
    }

}