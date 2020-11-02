package com.filit.testweather.view.weather

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.filit.domain.model.WeatherForecastModel
import com.filit.testweather.R

class WeatherForecastPagerAdapter(
    private val weatherForecastList: List<WeatherForecastModel>,
    fragmentManager: FragmentManager,
    private val ctx: Context
): FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    /*private var weatherForecastList= mutableListOf<WeatherForecastModel>()
    fun setData( weatherForecastList: List<WeatherForecastModel>){
        this.weatherForecastList.clear()
        this.weatherForecastList.addAll(weatherForecastList)
        notifyDataSetChanged()
    }*/
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ThreeDaysWeatherForecastFragment.newInstance(weatherForecastList.take(3))
            1 -> SevenDaysWeatherForecastFragment.newInstance(weatherForecastList.take(7))
            else -> throw RuntimeException("Page not found")
        }
    }

    override fun getPageTitle(position: Int) =
        ctx.getString(
            when (position) {
                0 -> R.string.threeDays
                1 -> R.string.sevenDays
                else -> throw RuntimeException("Page not found")
            }
        )

    override fun getCount(): Int = 2
}