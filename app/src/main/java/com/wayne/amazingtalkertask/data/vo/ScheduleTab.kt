package com.wayne.amazingtalkertask.data.vo

import com.google.gson.annotations.SerializedName

data class ScheduleTab (
    @SerializedName("start")
    val start: Long,
    @SerializedName("end")
    val end: Long,
    @SerializedName("title")
    val title: String
)
