package com.filit.testweather.view.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.filit.domain.model.WeatherForecastModel
import com.filit.testweather.R
import kotlinx.android.synthetic.main.f_weather_forecast_fragment.*

class SevenDaysWeatherForecastFragment(private val weatherForecastList: List<WeatherForecastModel>): Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.f_weather_forecast_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val weatherForecastAdapter = WeatherForecastAdapter(weatherForecastList)
        rvForecastWeather.adapter = weatherForecastAdapter
        rvForecastWeather.setHasFixedSize(true)
        rvForecastWeather.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    }
    companion object {
        fun newInstance(weatherForecastList: List<WeatherForecastModel>): SevenDaysWeatherForecastFragment {
            val fragment = SevenDaysWeatherForecastFragment(weatherForecastList)
            return fragment
        }
    }
}