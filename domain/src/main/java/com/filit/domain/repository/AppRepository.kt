package com.filit.domain.repository

interface AppRepository {
    fun getCurrentCity(): String
    fun getCities(): List<String>
    fun saveCity(city: String)
    fun changeCurrentCity (city: String)
}