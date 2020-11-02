package com.filit.testweather.common.extension

import android.R.attr.name
import android.content.Context
import android.content.SharedPreferences
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


fun SharedPreferences.saveCityList(list: List<String>) {
    val citiesList: MutableList<String> = this.getCities().toMutableList()
    list.forEach {
    if (!citiesList.contains(it)) {
        citiesList.add(it)
    }
        val gson = Gson()
        val json = gson.toJson(citiesList)
        this.edit().putString(Constants.SAVE_CITY, json).apply()
    }

}

fun SharedPreferences.getCities(): List<String> {
    val gson = Gson()
    val json: String = this.getString(Constants.SAVE_CITY, null) ?: ""
   return try {
        val type = object : TypeToken<List<String>>() {}.type
        gson.fromJson(json, type)
    }catch (e: Throwable){
       emptyList<String>()}
}