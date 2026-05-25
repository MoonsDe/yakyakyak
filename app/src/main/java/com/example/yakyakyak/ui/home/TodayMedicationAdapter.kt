package com.example.yakyakyak.ui.home

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.yakyakyak.data.MedicationLog
import com.example.yakyakyak.databinding.ItemTodayMedicationBinding

class TodayMedicationAdapter(
    private val onToggle: (MedicationLog) -> Unit
) : ListAdapter<MedicationLog, TodayMedicationAdapter.ViewHolder>(DiffCallback) {

    inner class ViewHolder(private val binding: ItemTodayMedicationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(log: MedicationLog) {
            binding.tvMedicationName.text = log.medicationName
            binding.tvScheduledTime.text = "⏰ ${log.scheduledTime}"
            binding.checkboxTaken.isChecked = log.isTaken

            val ctx = binding.root.context
            if (log.isTaken) {
                binding.tvMedicationName.paintFlags =
                    binding.tvMedicationName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                binding.tvMedicationName.alpha = 0.5f
                binding.tvScheduledTime.paintFlags =
                    binding.tvScheduledTime.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                binding.tvTakenAt.visibility = android.view.View.VISIBLE
                binding.tvTakenAt.text = "✓ 복용 완료 ${log.takenAt}"
                binding.viewStatusBar.setBackgroundColor(
                    ctx.getColor(com.example.yakyakyak.R.color.success)
                )
            } else {
                binding.tvMedicationName.paintFlags =
                    binding.tvMedicationName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                binding.tvMedicationName.alpha = 1f
                binding.tvScheduledTime.paintFlags =
                    binding.tvScheduledTime.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                binding.tvTakenAt.visibility = android.view.View.GONE
                binding.viewStatusBar.setBackgroundColor(
                    ctx.getColor(com.example.yakyakyak.R.color.primary)
                )
            }

            binding.checkboxTaken.setOnClickListener {
                onToggle(log)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTodayMedicationBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object DiffCallback : DiffUtil.ItemCallback<MedicationLog>() {
        override fun areItemsTheSame(oldItem: MedicationLog, newItem: MedicationLog) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MedicationLog, newItem: MedicationLog) =
            oldItem == newItem
    }
}
