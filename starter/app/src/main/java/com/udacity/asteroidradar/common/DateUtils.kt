package com.udacity.asteroidradar.common

import java.text.SimpleDateFormat
import java.util.*

private val dateFormatter = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.ENGLISH)

fun String.toDate(): Date {
    return dateFormatter.parse(this)!!
}

fun Date.toFormattedString(): String {
    return dateFormatter.format(this)
}

object DateUtils {

    fun getToday(): Date {
        return Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time
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
}
