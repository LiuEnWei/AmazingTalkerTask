package com.wayne.amazingtalkertask.data.vo

import com.google.gson.annotations.SerializedName

data class TimeRangeResponse(
    @SerializedName("start")
    val start: String?,
    @SerializedName("end")
    val end: String?
)
