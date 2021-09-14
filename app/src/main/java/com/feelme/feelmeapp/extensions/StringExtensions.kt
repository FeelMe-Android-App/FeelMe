package com.feelme.feelmeapp.extensions

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun String.getFullImageUrl() = "https://image.tmdb.org/t/p/w500${this}"
fun String.getYear(): String {
    val time = Calendar.getInstance()
    var date: Date? = null
    try {
         date = SimpleDateFormat("yyyy-MM-dd").parse(this)
    } catch (e: Exception) {
        return this
    }
    time.time = date
    return time.get(Calendar.YEAR).toString()
}
fun Int.getDuration(): String {
    val hours = TimeUnit.MINUTES.toHours(this.toLong())
    val remainMinutes = this - TimeUnit.HOURS.toMinutes(hours)
    return String.format("%dh%02dmin", hours, remainMinutes)
}