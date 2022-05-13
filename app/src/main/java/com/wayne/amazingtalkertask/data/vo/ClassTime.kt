package com.wayne.amazingtalkertask.data.vo

import com.google.gson.annotations.SerializedName

data class ClassTime(
    @SerializedName("start")
    val start: Long,
    @SerializedName("end")
    val end: Long,
    @SerializedName("isAvailable")
    val isAvailable: Boolean
)
