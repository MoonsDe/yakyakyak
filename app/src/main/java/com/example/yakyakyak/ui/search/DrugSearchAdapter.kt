package com.example.yakyakyak.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.yakyakyak.R
import com.example.yakyakyak.api.DrugItem

class DrugSearchAdapter : ListAdapter<DrugItem, DrugSearchAdapter.ViewHolder>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<DrugItem>() {
            override fun areItemsTheSame(a: DrugItem, b: DrugItem) = a.name == b.name
            override fun areContentsTheSame(a: DrugItem, b: DrugItem) = a == b
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_drug_name)
        val tvCompany: TextView = view.findViewById(R.id.tv_company)
        val layoutEfficacy: LinearLayout = view.findViewById(R.id.layout_efficacy)
        val tvEfficacy: TextView = view.findViewById(R.id.tv_efficacy)
        val layoutUsage: LinearLayout = view.findViewById(R.id.layout_usage)
        val tvUsage: TextView = view.findViewById(R.id.tv_usage)
        val layoutCaution: LinearLayout = view.findViewById(R.id.layout_caution)
        val tvCaution: TextView = view.findViewById(R.id.tv_caution)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_drug_search, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.tvName.text = item.name ?: "이름 없음"
        holder.tvCompany.text = item.company ?: ""

        fun bindSection(layout: LinearLayout, tv: TextView, text: String?) {
            if (!text.isNullOrBlank()) {
                layout.visibility = View.VISIBLE
                tv.text = text
            } else {
                layout.visibility = View.GONE
            }
        }

        bindSection(holder.layoutEfficacy, holder.tvEfficacy, item.efficacy)
        bindSection(holder.layoutUsage, holder.tvUsage, item.usage)

        val cautionText = listOfNotNull(item.warning, item.caution)
            .filter { it.isNotBlank() }
            .joinToString("\n\n")
        bindSection(holder.layoutCaution, holder.tvCaution, cautionText.ifBlank { null })
    }
}
