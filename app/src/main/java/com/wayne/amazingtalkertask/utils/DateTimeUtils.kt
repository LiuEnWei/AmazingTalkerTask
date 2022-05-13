package com.wayne.amazingtalkertask.utils

import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {
    const val FIRST_DAY_OF_WEEK_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:00:00.000'Z'"
    private const val API_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    const val YEAR_MONTH_DAY_FORMAT = "yyyy-MM-dd"
    const val MONTH_DAY_FORMAT = "MM-dd"
    const val WEEK_MONTH_DAY_DATE_TIME_FORMAT = "EEE, MMM d"
    const val TIME_FORMAT = "HH:mm"
    const val TIME_ZONE_ID = "UTC"

    fun Calendar.toFirstDayOfWeek(): Calendar {
        val firstDayOfWeek = this.firstDayOfWeek
        val dayOfWeek = this.get(Calendar.DAY_OF_WEEK)
        this.set(Calendar.HOUR_OF_DAY, 0)
        this.set(Calendar.MINUTE, 0)
        this.set(Calendar.SECOND, 0)
        this.set(Calendar.MILLISECOND, 0)
        this.add(Calendar.DATE, firstDayOfWeek - dayOfWeek)
        return this
    }

    fun Calendar.addDay(day: Int): Calendar {
        add(Calendar.DATE, day)
        return this
    }

    fun Long.formatToString(format: String, timeZoneId: String? = null): String {
        return try {
            val dateFormat = SimpleDateFormat(format, Locale.getDefault())
            timeZoneId?.let {
                dateFormat.timeZone = TimeZone.getTimeZone(timeZoneId)
            }
            val date = Date(this)
            dateFormat.format(date)
        } catch (e: Exception) {
            ""
        }
    }

    fun Calendar.formatToString(format: String, timeZoneId: String? = null): String {
        return this.timeInMillis.formatToString(format, timeZoneId)
    }

    fun String.toTimeInMillisOrNull(format: String = API_DATE_TIME_FORMAT, timeZoneId: String = TIME_ZONE_ID): Long? {
        return try {
            val dateFormat = SimpleDateFormat(format, Locale.getDefault())
            dateFormat.timeZone = TimeZone.getTimeZone(timeZoneId)
            val date = dateFormat.parse(this)
            date?.time
        } catch (e: Exception) {
            null
        }
    }
}

