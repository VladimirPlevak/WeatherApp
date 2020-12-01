package com.filit.testweather.view.weather

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.filit.domain.model.WeatherLoadModel
import com.filit.testweather.BuildConfig
import com.filit.testweather.R
import com.filit.testweather.common.extension.*
import com.filit.testweather.view.BaseActivity
import com.filit.testweather.view.cities.CitiesActivity
import com.filit.testweather.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.activity_main.*

class WeatherActivity : BaseActivity<WeatherViewModel>(WeatherViewModel::class) {

    companion object {
        fun newIntent(context: Context, city: String) =
            Intent(context, WeatherActivity::class.java)
                .putExtra(Intent.EXTRA_TEXT, city)
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_cities.setOnClickListener {
            viewModel.clickOnCitiesButton()
        }
        srlRefreshWeather.setOnRefreshListener { viewModel.refreshLoadWeather() }


        viewModel.weatherState.observe(this) {
            tvCityWeather.text = it.city
            tvCurrentDate.text = it.date?.toDateWithDayOfWeek()
            tvCurrentTemperature.text = it.temperature
            tvWind.text = it.wind
            tvHumidity.text = it.humidity
            tvPressure.text = it.pressure
            tvTemperatureFelt.text = it.tempFeelsLike
            val url = it.cloudy_URL.createLargeCloudyImageUrl()
            ic_weather.loadImage(url)
            tlWeatherForecastTabs.setupWithViewPager(vpWeatherForecastPager)
            val adapter = WeatherForecastPagerAdapter(it.weatherForecastList?: listOf(), this)
            vpWeatherForecastPager.adapter = adapter
            vpWeatherForecastPager.currentItem = viewModel.selectedPagePosition
            vpWeatherForecastPager.addOnPageSelectedListener{
                viewModel.pageSelect(it)
            }


        }
        viewModel.contentVisibilityState.observe(this) { visibilityState ->
            tvCityWeather.applyVisibilityState(visibilityState)
            tvCurrentDate.applyVisibilityState(visibilityState)
            tvCurrentTemperature.applyVisibilityState(visibilityState)
            tvTemperatureFelt.applyVisibilityState(visibilityState)
            additionalInfo.applyVisibilityState(visibilityState)
            btn_cities.applyVisibilityState(visibilityState)
            ic_weather.applyVisibilityState(visibilityState)
            vpWeatherForecastPager.applyVisibilityState(visibilityState)
            tlWeatherForecastTabs.applyVisibilityState(visibilityState)
        }
        viewModel.loadProgressVisibilityState.observe(this){visibilityState->
            pgLoading.applyVisibilityState(visibilityState)
        }
        viewModel.errorState.observe(this){
            showToastError(it.error)
        }

        viewModel.refreshEnable.observe(this) { srlRefreshWeather.isEnabled = it }
        viewModel.refreshState.observe(this) { srlRefreshWeather.isRefreshing = it }
        viewModel.openCitiesActivity.observe(this){
            startActivity(CitiesActivity.newIntent(this))
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(CitiesActivity.newIntent(this))
    }
}
