package com.filit.testweather.view.weather

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.filit.domain.model.WeatherForecastModel
import com.filit.testweather.R
import kotlinx.android.synthetic.main.f_weather_forecast_fragment.view.*

class WeatherForecastPagerAdapter(
    private val weatherForecastList: List<WeatherForecastModel>,
    private val ctx: Context
): PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context)
            .inflate(R.layout.f_weather_forecast_fragment, container, false)
        val weatherForecastAdapter = WeatherForecastAdapter(getItem(position = position))
        view.rvForecastWeather.adapter = weatherForecastAdapter
        view.rvForecastWeather.setHasFixedSize(true)
        view.rvForecastWeather.layoutManager = LinearLayoutManager(container.context, RecyclerView.VERTICAL, false)
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view = `object` as View
        container.removeView(view)
    }

    override fun getPageTitle(position: Int) =
        ctx.getString(
            when (position) {
                0 -> R.string.threeDays
                1 -> R.string.sevenDays
                else -> throw RuntimeException("Page not found")
            }
        )

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int = 2

    private fun getItem(position: Int): List<WeatherForecastModel> {
        return when (position) {
            0 -> weatherForecastList.take(3)
            1 -> weatherForecastList.take(7)
            else -> throw RuntimeException("Page not found")
        }
    }
}