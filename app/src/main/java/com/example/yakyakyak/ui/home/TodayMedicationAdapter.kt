package com.example.yakyakyak.ui.home

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.yakyakyak.data.MedicationLog
import com.example.yakyakyak.databinding.ItemTodayMedicationBinding

class TodayMedicationAdapter(
    private val onToggle: (MedicationLog) -> Unit,
    private val onMealDone: (MedicationLog) -> Unit
) : ListAdapter<MedicationLog, TodayMedicationAdapter.ViewHolder>(DiffCallback) {

    inner class ViewHolder(private val binding: ItemTodayMedicationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(log: MedicationLog) {
            binding.tvMedicationName.text = log.medicationName

            // 복용 시간 + 복용 방법 표시
            val hasMealTiming = log.mealTiming != "없음" && log.mealTiming.isNotBlank()
            binding.tvScheduledTime.text = if (hasMealTiming) {
                "⏰ ${log.scheduledTime}  •  ${log.mealTiming}"
            } else {
                "⏰ ${log.scheduledTime}"
            }

            binding.checkboxTaken.isChecked = log.isTaken

            val ctx = binding.root.context

            if (log.isTaken) {
                binding.tvMedicationName.paintFlags =
                    binding.tvMedicationName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                binding.tvMedicationName.alpha = 0.5f
                binding.tvScheduledTime.paintFlags =
                    binding.tvScheduledTime.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                binding.tvTakenAt.visibility = View.VISIBLE
                binding.tvTakenAt.text = "✓ 복용 완료 ${log.takenAt}"
                binding.tvTakenAt.setTextColor(ctx.getColor(com.example.yakyakyak.R.color.success))
                binding.viewStatusBar.setBackgroundColor(
                    ctx.getColor(com.example.yakyakyak.R.color.success)
                )
                binding.btnMealDone.visibility = View.GONE
            } else {
                binding.tvMedicationName.paintFlags =
                    binding.tvMedicationName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                binding.tvMedicationName.alpha = 1f
                binding.tvScheduledTime.paintFlags =
                    binding.tvScheduledTime.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                binding.viewStatusBar.setBackgroundColor(
                    ctx.getColor(com.example.yakyakyak.R.color.primary)
                )

                // 식사 완료 버튼 / 알람 설정 안내
                val isMealDelayType = log.mealTiming == "식후 30분" || log.mealTiming == "식후 1시간"
                when {
                    log.mealDoneAt != null -> {
                        binding.btnMealDone.visibility = View.GONE
                        binding.tvTakenAt.visibility = View.VISIBLE
                        binding.tvTakenAt.text = "⏰ ${log.mealTiming} 알람 설정됨 (${log.mealDoneAt})"
                        binding.tvTakenAt.setTextColor(ctx.getColor(com.example.yakyakyak.R.color.primary))
                    }
                    isMealDelayType -> {
                        binding.btnMealDone.visibility = View.VISIBLE
                        binding.tvTakenAt.visibility = View.GONE
                    }
                    else -> {
                        binding.btnMealDone.visibility = View.GONE
                        binding.tvTakenAt.visibility = View.GONE
                    }
                }
            }

            binding.checkboxTaken.setOnClickListener {
                onToggle(log)
            }

            binding.btnMealDone.setOnClickListener {
                onMealDone(log)
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
