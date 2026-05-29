package com.example.yakyakyak.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.yakyakyak.R
import com.example.yakyakyak.data.MedicationLog
import com.example.yakyakyak.databinding.ItemHistoryLogBinding

class HistoryAdapter : ListAdapter<MedicationLog, HistoryAdapter.ViewHolder>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<MedicationLog>() {
            override fun areItemsTheSame(a: MedicationLog, b: MedicationLog) = a.id == b.id
            override fun areContentsTheSame(a: MedicationLog, b: MedicationLog) = a == b
        }
    }

    inner class ViewHolder(private val binding: ItemHistoryLogBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(log: MedicationLog) {
            binding.tvMedName.text = log.medicationName
            val timeText = if (log.isTaken && log.takenAt != null)
                "예정: ${log.scheduledTime}  ·  실제: ${log.takenAt}"
            else
                "예정: ${log.scheduledTime}"
            binding.tvScheduledTime.text = timeText

            if (log.isTaken) {
                binding.tvStatusIcon.text = "✅"
                binding.tvStatusBadge.text = "복용 완료"
                binding.tvStatusBadge.setTextColor(
                    ContextCompat.getColor(binding.root.context, R.color.success)
                )
            } else {
                binding.tvStatusIcon.text = "⏳"
                binding.tvStatusBadge.text = "미복용"
                binding.tvStatusBadge.setTextColor(
                    ContextCompat.getColor(binding.root.context, R.color.text_secondary)
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemHistoryLogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))
}
