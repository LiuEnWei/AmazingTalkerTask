package com.wayne.amazingtalkertask.ui.teacherschedule

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayoutMediator
import com.wayne.amazingtalkertask.R
import com.wayne.amazingtalkertask.databinding.FragmentTeacherScheduleBinding
import com.wayne.amazingtalkertask.ui.TeacherScheduleAdapter
import com.wayne.amazingtalkertask.ui.base.BaseFragment
import com.wayne.amazingtalkertask.utils.DateTimeUtils.toFirstDayOfWeek
import java.util.*

class TeacherScheduleFragment : BaseFragment<FragmentTeacherScheduleBinding>(R.layout.fragment_teacher_schedule) {

    private val viewModel by viewModels<TeacherScheduleViewModel>()

    private lateinit var teacherScheduleAdapter: TeacherScheduleAdapter

    override fun bindView(view: View): FragmentTeacherScheduleBinding {
        return FragmentTeacherScheduleBinding.bind(view)
    }

    override fun init() {
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
