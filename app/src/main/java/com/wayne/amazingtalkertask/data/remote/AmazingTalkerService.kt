package com.wayne.amazingtalkertask.data.remote

import com.wayne.amazingtalkertask.data.vo.ScheduleResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AmazingTalkerService {

    @GET("/v1/guest/teachers/{teacherName}/schedule")
    suspend fun getTeacherSchedule(
        @Path("teacherName")
        teacherName: String,
        @Query("started_at")
        startedAt: String
    ): ScheduleResponse
}
