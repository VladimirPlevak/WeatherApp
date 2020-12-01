package com.filit.testweather.common.extension

import android.net.Uri

fun String.createLargeCloudyImageUrl(): String {
    return "http://openweathermap.org/img/wn/${Uri.encode("$this@4x")}.png"
}

fun String.createSmallCloudyImageUrl(): String =
    "http://openweathermap.org/img/wn/${this}@2x.png"
