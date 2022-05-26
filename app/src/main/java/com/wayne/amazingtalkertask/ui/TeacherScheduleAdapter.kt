package com.wayne.amazingtalkertask.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.wayne.amazingtalkertask.data.vo.ClassTime
import com.wayne.amazingtalkertask.data.vo.ScheduleTab
import com.wayne.amazingtalkertask.databinding.ItemTeacherScheduleBinding
import kotlinx.coroutines.launch

class TeacherScheduleAdapter(val scheduleTabs: List<ScheduleTab>,
                             private val lifecycleOwner: LifecycleOwner,
                             private val teacherScheduleCallBack: TeacherScheduleCallBack) : RecyclerView.Adapter<TeacherScheduleAdapter.ViewHolder>() {

    interface TeacherScheduleCallBack {
        suspend fun getSchedule(scheduleTab: ScheduleTab, data: List<ClassTime>): List<ClassTime>
    }

    private var schedule: List<ClassTime> = arrayListOf()

    fun updateClassTime(schedule: List<ClassTime>) {
        this.schedule = schedule
    }

    override fun getItemCount(): Int {
        return scheduleTabs.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTeacherScheduleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(schedule, position)
    }

    inner class ViewHolder(private val binding: ItemTeacherScheduleBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: List<ClassTime>, position: Int) {
            lifecycleOwner.lifecycleScope.launch {
                if (position >= scheduleTabs.size) {
                    return@launch
                }
                val dailySchedule = teacherScheduleCallBack.getSchedule(scheduleTabs[position], data)
                binding.recyclerviewDailySchedule.adapter = DailyScheduleAdapter(dailySchedule)
            }
        }
    }
}
