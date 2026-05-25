package com.example.yakyakyak.ui.medication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.yakyakyak.data.Medication
import com.example.yakyakyak.databinding.ItemMedicationBinding

class MedicationAdapter(
    private val onEdit: (Medication) -> Unit,
    private val onDelete: (Medication) -> Unit
) : ListAdapter<Medication, MedicationAdapter.ViewHolder>(DiffCallback) {

    inner class ViewHolder(private val binding: ItemMedicationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(medication: Medication) {
            binding.tvName.text = medication.name
            binding.tvTimes.text = "복용 시간: ${medication.times.replace(",", ", ")}"
            binding.tvCycle.text = "복용 주기: ${medication.cycle}"
            binding.tvPeriod.text = "복용 기간: ${medication.startDate} ~ ${medication.endDate}"

            if (medication.memo.isNotBlank()) {
                binding.tvMemo.visibility = View.VISIBLE
                binding.tvMemo.text = "메모: ${medication.memo}"
            } else {
                binding.tvMemo.visibility = View.GONE
            }

            // 활성 여부 표시
            binding.tvStatus.text = if (medication.isActive) "복용 중" else "복용 종료"
            binding.tvStatus.setTextColor(
                if (medication.isActive)
                    binding.root.context.getColor(com.example.yakyakyak.R.color.success)
                else
                    binding.root.context.getColor(com.example.yakyakyak.R.color.text_secondary)
            )

            binding.btnEdit.setOnClickListener { onEdit(medication) }
            binding.btnDelete.setOnClickListener { onDelete(medication) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMedicationBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Medication>() {
        override fun areItemsTheSame(oldItem: Medication, newItem: Medication) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Medication, newItem: Medication) =
            oldItem == newItem
    }
}
