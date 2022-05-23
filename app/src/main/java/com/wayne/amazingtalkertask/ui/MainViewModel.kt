package com.wayne.amazingtalkertask.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayne.amazingtalkertask.Constant
import com.wayne.amazingtalkertask.data.vo.ClassTime
import com.wayne.amazingtalkertask.data.vo.ScheduleTab
import com.wayne.amazingtalkertask.data.vo.TeacherSchedule
import com.wayne.amazingtalkertask.data.repository.AmazingTalkerRepository
import com.wayne.amazingtalkertask.utils.DateTimeUtils
import com.wayne.amazingtalkertask.utils.DateTimeUtils.addDay
import com.wayne.amazingtalkertask.utils.DateTimeUtils.formatToString
import com.wayne.amazingtalkertask.utils.DateTimeUtils.toTimeInMillisOrNull
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*
import java.util.concurrent.TimeUnit

class MainViewModel() : ViewModel(), TeacherScheduleAdapter.TeacherScheduleViewModel, KoinComponent {
    private val amazingTalkerRepository: AmazingTalkerRepository by inject()

    private val day1 = TimeUnit.DAYS.toMillis(1)
    private val minutes30 = TimeUnit.MINUTES.toMillis(30)

    private val _teacherSchedule = MutableLiveData<TeacherSchedule>()
    val teacherSchedule: LiveData<TeacherSchedule> = _teacherSchedule

    private lateinit var firstDayOfWeek: Calendar

    private val _schedule = MutableLiveData<List<ClassTime>>()
    val schedule: LiveData<List<ClassTime>> = _schedule

    fun updateFirstDayOfWeek(calendar: Calendar) {
        this.firstDayOfWeek = calendar
        updateTeacherSchedule(calendar.time)
        fetchTeacherSchedule(Constant.TEACHER_NAME, calendar.timeInMillis)
    }

    fun lastWeek() {
        updateFirstDayOfWeek(firstDayOfWeek.addDay(-7))
    }

    fun nextWeek() {
        updateFirstDayOfWeek(firstDayOfWeek.addDay(7))
    }

    private fun updateTeacherSchedule(firstDayOfWeek: Date) {
        val calendar = Calendar.getInstance()

        val title = firstDayOfWeek.getTabTitle()
        val isEnabledLastWeek = firstDayOfWeek.time > calendar.timeInMillis

        val tabs: ArrayList<ScheduleTab> = arrayListOf()
        for (i in 0 until 7) {
            calendar.time = firstDayOfWeek
            val start = calendar.addDay(i).timeInMillis
            val end = start + day1
            val tabTitle = start.formatToString(DateTimeUtils.WEEK_MONTH_DAY_DATE_TIME_FORMAT)
            tabs.add(ScheduleTab(start, end, tabTitle))
        }
        _teacherSchedule.value = TeacherSchedule(
            title,
            isEnabledLastWeek,
            tabs
        )
    }

    private fun Date.getTabTitle(): String {
        return try {
            val calendar = Calendar.getInstance()
            calendar.time = this
            val title = StringBuilder()
            title.append(calendar.formatToString(DateTimeUtils.YEAR_MONTH_DAY_FORMAT))
            title.append(" - ")
            calendar.addDay(6)
            title.append(calendar.formatToString(DateTimeUtils.MONTH_DAY_FORMAT))
            title.toString()
        } catch (e: Exception) {
            ""
        }
    }

    private fun fetchTeacherSchedule(teacherName: String, startAtMillis: Long) {
        viewModelScope.launch {
            val startAt = startAtMillis.formatToString(DateTimeUtils.FIRST_DAY_OF_WEEK_DATE_TIME_FORMAT, DateTimeUtils.TIME_ZONE_ID)
            val response = amazingTalkerRepository.getTeacherSchedule(teacherName, startAt)
                .transform {
                    // 整理資料
                    it.available?.let { available ->
                        for (timeRange in available) {
                            val start = timeRange.start?.toTimeInMillisOrNull()
                            val end = timeRange.end?.toTimeInMillisOrNull()
                            val checkTime = start != null && end != null
                            if (checkTime) {
                                emit(ClassTime(start!!, end!!, true))
                            }
                        }
                    }
                    it.booked?.let { booked ->
                        for (timeRange in booked) {
                            val start = timeRange.start?.toTimeInMillisOrNull()
                            val end = timeRange.end?.toTimeInMillisOrNull()
                            val checkTime = start != null && end != null
                            if (checkTime) {
                                emit(ClassTime(start!!, end!!, false))
                            }
                        }
                    }
                }.transform {
                    // 拆分超過30分鐘的間隔
                    var start = it.start
                    val end = it.end
                    do {
                        val newStart = start + minutes30
                        emit(ClassTime(start, newStart, it.isAvailable))
                        start = newStart
                    } while (end - start >= minutes30)
                }.catch {
                }
                .toList()
            _schedule.value = response
        }
    }

    override suspend fun getSchedule(scheduleTab: ScheduleTab, data: List<ClassTime>): List<ClassTime> {
        return flow {
            data.forEach {
                emit(it)
            }
        }.filter {
            return@filter it.start >= scheduleTab.start && it.start < scheduleTab.end
        }.catch {
        }.toList()
        .sortedBy {
            it.start
        }
    }
}
