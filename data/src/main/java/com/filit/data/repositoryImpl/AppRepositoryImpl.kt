package com.filit.data.repositoryImpl

import android.app.Application
import android.content.Context
import com.filit.data.common.preferencesName.PreferencesName
import com.filit.domain.repository.AppRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AppRepositoryImpl (app: Application): AppRepository {

    private val cityPrefs = app.getSharedPreferences(PreferencesName.CITIES, Context.MODE_PRIVATE)
    private val currentCityPrefs = app.getSharedPreferences(PreferencesName.CITY, Context.MODE_PRIVATE)

    override fun getCurrentCity(): String =
        currentCityPrefs.getString(PreferencesName.CURRENT_CITY, "Moscow")?:"Moscow"


    override fun getCities(): List<String> {
        val gson = Gson()
        val json: String? = cityPrefs.getString(PreferencesName.CURRENT_CITIES, null)
        return try {
            val type = object : TypeToken<List<String>>() {}.type
            gson.fromJson(json, type)
        }catch (e: Throwable) {
            val listCities = listOf("Moscow" , "Minsk")
            val jsonCityList = gson.toJson(listCities)
            cityPrefs.edit().putString(PreferencesName.CURRENT_CITIES, jsonCityList).apply()
            listCities
        }
    }

    override fun saveCity(city: String) {
        val citiesList: MutableList<String> = this.getCities().toMutableList()
        if (!citiesList.contains(city)){
            citiesList.add(city)
            val gson = Gson()
            val json = gson.toJson(citiesList)
            cityPrefs.edit().putString(PreferencesName.CURRENT_CITIES, json).apply()
        }
    }

    override fun changeCurrentCity(city: String) {
        currentCityPrefs.edit().putString(PreferencesName.CURRENT_CITY, city).apply()
    }
}