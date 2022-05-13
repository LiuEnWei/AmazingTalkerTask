package com.wayne.amazingtalkertask.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayoutMediator
import com.wayne.amazingtalkertask.databinding.ActivityMainBinding
import com.wayne.amazingtalkertask.utils.DateTimeUtils.toFirstDayOfWeek
import org.koin.core.component.KoinComponent
import java.util.*

class MainActivity : AppCompatActivity(), KoinComponent {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    private lateinit var teacherScheduleAdapter: TeacherScheduleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupObserve()
        setupListener()
        setupUI()
    }

    private fun setupObserve() {
        viewModel.teacherSchedule.observe(
            this,
            Observer {
                binding.btnLastWeek.isEnabled = it.isEnabledLastWeek
                binding.textTitle.text = it.title
                teacherScheduleAdapter = TeacherScheduleAdapter(it.tabs, this, viewModel)
                binding.viewpagerSchedule.adapter = teacherScheduleAdapter

                TabLayoutMediator(binding.tabSchedule, binding.viewpagerSchedule) { tab, position ->
                    tab.text = it.tabs[position].title
                }.attach()
            }
        )

        viewModel.schedule.observe(
            this,
            Observer {
                teacherScheduleAdapter.updateClassTime(it)
                val currentItem = binding.viewpagerSchedule.currentItem
                teacherScheduleAdapter.notifyItemChanged(currentItem)
            }
        )
    }

    private fun setupListener() {
        binding.btnLastWeek.setOnClickListener {
            viewModel.lastWeek()
        }

        binding.btnNextWeek.setOnClickListener {
            viewModel.nextWeek()
        }
    }

    private fun setupUI() {
        viewModel.updateFirstDayOfWeek(Calendar.getInstance().toFirstDayOfWeek())
    }
}
