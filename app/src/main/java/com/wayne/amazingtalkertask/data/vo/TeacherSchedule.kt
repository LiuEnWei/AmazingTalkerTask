package com.wayne.amazingtalkertask.data.vo

import com.google.gson.annotations.SerializedName

data class TeacherSchedule(
    @SerializedName("title")
    val title: String,
    @SerializedName("isEnabledLastWeek")
    val isEnabledLastWeek: Boolean,
    @SerializedName("tabs")
    val tabs: List<ScheduleTab>
)
