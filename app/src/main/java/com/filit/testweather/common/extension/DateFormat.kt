package com.filit.testweather.common.extension

import java.text.SimpleDateFormat
import java.util.*

fun Long.toDateWithDayOfWeek (): String{
    val date = Date(this)
    val simpleFormat = SimpleDateFormat("EEEE, dd MMMM", Locale.US)
    return simpleFormat.format(date)
}
fun Long.toDayOfWeek (): String{
    val date = Date(this)
    val simpleFormat = SimpleDateFormat("EEEE", Locale.US)
    return simpleFormat.format(date)
}
fun Long.toDate (): String{
    val date = Date(this)
    val simpleFormat = SimpleDateFormat("dd MMMM", Locale.US)
    return simpleFormat.format(date)
}