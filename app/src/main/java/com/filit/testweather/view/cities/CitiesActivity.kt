package com.filit.testweather.view.cities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract
import androidx.recyclerview.widget.LinearLayoutManager
import com.filit.domain.model.CityLoadModel
import com.filit.domain.model.CityModel
import com.filit.testweather.BuildConfig
import com.filit.testweather.R
import com.filit.testweather.common.extension.*
import com.filit.testweather.view.BaseActivity
import com.filit.testweather.view.weather.WeatherActivity
import com.filit.testweather.viewmodel.CitiesViewModel
import kotlinx.android.synthetic.main.a_cities.*

class CitiesActivity : BaseActivity<CitiesViewModel>(CitiesViewModel::class) {

    companion object {
        fun newIntent(context: Context) =
            Intent(context, CitiesActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_cities)
        viewModel.loadCities()
        btnAddCity.setOnClickListener {
            viewModel.addCities(svAddCity.query.toString())
        }
        viewModel.citesState.observe(this) {
            val citiesLayoutManger = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            val citiesAdapter = CitiesAdapter(it,
                object : CitiesAdapter.Listener {
                    override fun onClick(position: Int) = viewModel.clickOnCity(position)
                })
            rvCities.apply {
                adapter = citiesAdapter
                layoutManager = citiesLayoutManger
                setHasFixedSize(true)
            }
        }

        viewModel.contentVisibilityState.observe(this) {
            svAddCity.applyVisibilityState(it)
            rvCities.applyVisibilityState(it)
            btnAddCity.applyVisibilityState(it)
        }
        viewModel.loadProgressVisibilityState.observe(this) {
            pgLoading.applyVisibilityState(it)
        }
        viewModel.errorState.observe(this) {
            showToastError(it.error)
        }
        viewModel.errorAddCity.observe(this) {
            showToastMessage(it)
        }
        viewModel.openWeather.observe(this){
            startActivity(WeatherActivity.newIntent(this, it.city))
        }
    }
}