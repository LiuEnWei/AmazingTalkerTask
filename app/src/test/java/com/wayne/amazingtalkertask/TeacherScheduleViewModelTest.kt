package com.wayne.amazingtalkertask

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.wayne.amazingtalkertask.data.vo.ScheduleResponse
import com.wayne.amazingtalkertask.repository.AmazingTalkerRepository
import com.wayne.amazingtalkertask.ui.teacherschedule.TeacherScheduleViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import java.util.*

class TeacherScheduleViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var instantExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    @ExperimentalCoroutinesApi
    val mainDispatcher = MainCoroutineScopeRule()

    private lateinit var viewModel: TeacherScheduleViewModel
    private lateinit var repository: AmazingTalkerRepository
    private val fakeResponse: ScheduleResponse = FakeData.getScheduleResponse()
    private val testTimeMillis = 1651939200000

    @Before
    @ExperimentalCoroutinesApi
    fun setupMainViewModel() {
        repository = mock()

        startKoin {
            modules(
                module {
                    single { repository }
                }
            )
        }
        runBlocking {
            whenever(repository.getTeacherSchedule(Constant.TEACHER_NAME, "2022-05-07T16:00:00.000Z"))
                .thenReturn(flow { emit(fakeResponse) })
        }

        viewModel = TeacherScheduleViewModel()
    }

    @Test
    @ExperimentalCoroutinesApi
    fun teacherScheduleTest() = mainDispatcher.runBlockingTest {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = testTimeMillis
        viewModel.updateFirstDayOfWeek(calendar)
        viewModel.teacherSchedule.observeForever(Observer {
            assert("2022-05-08 - 05-14" == it.title)
            assert(false == it.isEnabledLastWeek)
            assert(it.tabs.size == 7)
        })
    }

    @Test
    @ExperimentalCoroutinesApi
    fun scheduleTest() = mainDispatcher.runBlockingTest {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = testTimeMillis
        viewModel.updateFirstDayOfWeek(calendar)
        viewModel.schedule.observeForever(Observer {
            assert(it.size == 15)
        })
    }

    @After
    fun end() {
        stopKoin()
    }
}
