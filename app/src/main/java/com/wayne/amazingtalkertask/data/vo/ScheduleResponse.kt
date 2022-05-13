package com.wayne.amazingtalkertask.data.vo

import com.google.gson.annotations.SerializedName

data class ScheduleResponse(
    @SerializedName("available")
    val available: List<TimeRangeResponse>?,
    @SerializedName("booked")
    val booked: List<TimeRangeResponse>?
)
