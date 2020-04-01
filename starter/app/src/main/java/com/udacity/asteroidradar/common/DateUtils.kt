package com.udacity.asteroidradar.common

import java.text.SimpleDateFormat
import java.util.*

private val dateFormatter = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.ENGLISH)

fun String.toDate(): Date? {
    return dateFormatter.parse(this)
}

fun Date.toFormattedString(): String {
    return dateFormatter.format(this)
}

fun getToday(): Date {
    return Calendar.getInstance().time
}

fun getDateAfter(
    daysAmount: Int,
    startDate: Date = getToday()
): Date {
    return Calendar.getInstance().apply {
        time = startDate
        add(Calendar.DAY_OF_YEAR, daysAmount)
    }.time
}
