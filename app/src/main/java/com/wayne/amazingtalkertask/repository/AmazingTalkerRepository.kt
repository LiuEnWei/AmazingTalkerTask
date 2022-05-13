package com.wayne.amazingtalkertask.repository

import com.wayne.amazingtalkertask.data.remote.AmazingTalkerService
import com.wayne.amazingtalkertask.data.vo.ScheduleResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface AmazingTalkerRepository {
    fun getTeacherSchedule(teacherName: String, startedAt: String): Flow<ScheduleResponse>
}

class AmazingTalkerRepositoryImpl(): AmazingTalkerRepository, KoinComponent  {
    private val api: AmazingTalkerService by inject()

    override fun getTeacherSchedule(teacherName: String, startedAt: String): Flow<ScheduleResponse> {
        return flow {
            emit(api.getTeacherSchedule(teacherName, startedAt))
        }
    }
}
