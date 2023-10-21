package com.example.weatherforecast.utils

import java.text.SimpleDateFormat
import java.util.*

val <T>T.exhaustive: T
    get() = this

fun formatDate(timeStamp: Long): String {

    val sdf = SimpleDateFormat("EE, MMM dd", Locale.ENGLISH)
    sdf.timeZone = TimeZone.getTimeZone("Asia/Kolkata")
    val date = Date(timeStamp * 1000 )
    return sdf.format(date)
}

fun formatDateTime(timestamp: Int): String {
    val sdf = SimpleDateFormat("hh:mm:aa", Locale.ENGLISH)
    sdf.timeZone = TimeZone.getTimeZone("Asia/Kolkata")
    val date = Date(timestamp.toLong() * 1000)

    return sdf.format(date)
}

fun formatDecimal(item: Double) = "%.0f".format(item)