package com.wayne.amazingtalkertask.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wayne.amazingtalkertask.data.vo.ClassTime
import com.wayne.amazingtalkertask.databinding.ItemDailyScheduleBinding
import com.wayne.amazingtalkertask.utils.DateTimeUtils
import com.wayne.amazingtalkertask.utils.DateTimeUtils.formatToString

class DailyScheduleAdapter(private val dailySchedule: List<ClassTime>) : RecyclerView.Adapter<DailyScheduleAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return dailySchedule.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDailyScheduleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position >= dailySchedule.size) {
            return
        }
        holder.bind(dailySchedule[position])
    }

    inner class ViewHolder(private val binding: ItemDailyScheduleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ClassTime) {
            binding.btnClass.isEnabled = data.isAvailable
            binding.btnClass.text = data.start.formatToString(DateTimeUtils.TIME_FORMAT)
        }
    }
}
