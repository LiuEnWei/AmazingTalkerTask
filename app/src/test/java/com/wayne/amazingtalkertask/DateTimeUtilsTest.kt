package com.wayne.amazingtalkertask

import com.wayne.amazingtalkertask.utils.DateTimeUtils.addDay
import com.wayne.amazingtalkertask.utils.DateTimeUtils.formatToString
import com.wayne.amazingtalkertask.utils.DateTimeUtils.toFirstDayOfWeek
import com.wayne.amazingtalkertask.utils.DateTimeUtils.toTimeInMillisOrNull
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class DateTimeUtilsTest {

    @Test
    fun toFirstDayOfWeek() {
        val format = SimpleDateFormat("yyyy-MM-dd")
        val today = "2022-05-13"
        val firstDayOfWeek = "2022-05-08"
        val calendar = Calendar.getInstance()
        val todayMillis = format.parse(today).time
        val firstDayOfWeekMillis = format.parse(firstDayOfWeek).time
        calendar.timeInMillis = todayMillis
        assert(calendar.toFirstDayOfWeek().timeInMillis == firstDayOfWeekMillis)
    }

    @Test
    fun addDay() {
        val format = SimpleDateFormat("yyyy-MM-dd")
        val today = "2022-05-13"
        val tomorrow = "2022-05-14"
        val calendar = Calendar.getInstance()
        val todayMillis = format.parse(today).time
        val tomorrowMillis = format.parse(tomorrow).time
        calendar.timeInMillis = todayMillis
        assert(calendar.addDay(1).timeInMillis == tomorrowMillis)
    }

    @Test
    fun formatToString() {
        val format = "yyyy-MM-dd HH:mm:ss"
        val timeInMillis = 1652371200000
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeInMillis
        val answer = "2022-05-13 00:00:00"
        assert(answer == calendar.formatToString(format))
        assert(answer == timeInMillis.formatToString(format))
    }

    @Test
    fun toTimeInMillisOrNull() {
        val time = "2022-05-13 00:00:00"
        val format = "yyyy-MM-dd HH:mm:ss"
        val zoneId = "GMT+8"
        val millis = time.toTimeInMillisOrNull(format, zoneId)
        assert(1652371200000 == millis)
    }
}
