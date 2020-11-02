package com.filit.testweather.common.extension

fun String.createLargeCloudyImageUrl(): String =
    "http://openweathermap.org/img/wn/${this}@4x.png"

fun String.createSmallCloudyImageUrl(): String =
    "http://openweathermap.org/img/wn/${this}@2x.png"
