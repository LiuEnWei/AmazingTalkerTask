package com.wayne.amazingtalkertask

import com.google.gson.Gson
import com.wayne.amazingtalkertask.data.vo.ScheduleResponse

object FakeData {
    fun getScheduleResponse(): ScheduleResponse {
        /**
         * available : 4:30 - 6:00 , 10:30 - 11:00
         * booked : 6:00 - 10:30, 11:00-12:00
         * */
        val json = "{\"available\":[{\"start\":\"2022-05-13T04:30:00Z\",\"end\":\"2022-05-13T06:00:00Z\"},{\"start\":\"2022-05-13T10:30:00Z\",\"end\":\"2022-05-13T11:00:00Z\"}],\"booked\":[{\"start\":\"2022-05-13T06:00:00Z\",\"end\":\"2022-05-13T10:30:00Z\"},{\"start\":\"2022-05-13T11:00:00Z\",\"end\":\"2022-05-13T12:00:00Z\"}]}"
        return Gson().fromJson(json, ScheduleResponse::class.java)
    }
}
