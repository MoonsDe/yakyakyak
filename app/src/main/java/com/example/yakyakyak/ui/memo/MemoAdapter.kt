package com.example.yakyakyak.ui.memo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.yakyakyak.data.HealthMemo
import com.example.yakyakyak.databinding.ItemMemoBinding

class MemoAdapter(
    private val onDelete: (HealthMemo) -> Unit,
    private val onClick: (HealthMemo) -> Unit
) : ListAdapter<HealthMemo, MemoAdapter.ViewHolder>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<HealthMemo>() {
            override fun areItemsTheSame(a: HealthMemo, b: HealthMemo) = a.id == b.id
            override fun areContentsTheSame(a: HealthMemo, b: HealthMemo) = a == b
        }
    }

    inner class ViewHolder(private val binding: ItemMemoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(memo: HealthMemo) {
            binding.tvMemoTitle.text = memo.title
            binding.tvMemoContent.text = memo.content
            binding.tvMemoDate.text = memo.date
            binding.btnDeleteMemo.setOnClickListener { onDelete(memo) }
            binding.root.setOnClickListener { onClick(memo) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemMemoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))
}
