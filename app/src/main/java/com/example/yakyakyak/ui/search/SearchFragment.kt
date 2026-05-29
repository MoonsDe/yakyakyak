package com.example.yakyakyak.ui.search

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yakyakyak.R
import com.example.yakyakyak.api.DrugItem
import com.example.yakyakyak.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: DrugSearchAdapter
    private var selectedCho: Char? = null

    private val choseongs = listOf('전', 'ㄱ', 'ㄴ', 'ㄷ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅅ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ')
    private val chipViews = mutableListOf<TextView>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = DrugSearchAdapter()
        binding.recyclerSearch.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@SearchFragment.adapter
        }

        setupChoseongButtons()
        showList(CommonDrugs.list)

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val q = s?.toString() ?: ""
                if (q.isBlank()) {
                    applyFilter()
                } else {
                    val filtered = CommonDrugs.filter(q)
                    if (filtered.isEmpty()) showEmpty() else showList(filtered)
                }
            }
        })

        binding.btnSearch.visibility = View.GONE
    }

    private fun setupChoseongButtons() {
        val ctx = requireContext()
        val primaryColor = ContextCompat.getColor(ctx, R.color.primary)

        choseongs.forEach { cho ->
            val chip = TextView(ctx).apply {
                text = if (cho == '전') "전체" else cho.toString()
                textSize = 14f
                setTextColor(primaryColor)
                setPadding(dp(14), dp(6), dp(14), dp(6))
                background = ContextCompat.getDrawable(ctx, R.drawable.bg_chip_unselected)
                layoutParams = ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply { marginEnd = dp(8) }

                setOnClickListener {
                    selectedCho = if (cho == '전') null else cho
                    binding.etSearch.text?.clear()
                    updateChipStyles(this)
                    applyFilter()
                }
            }
            chipViews.add(chip)
            binding.layoutChoseong.addView(chip)
        }
        // 전체 칩 기본 선택
        updateChipStyles(chipViews.first())
    }

    private fun updateChipStyles(selected: TextView) {
        val ctx = requireContext()
        val primaryColor = ContextCompat.getColor(ctx, R.color.primary)
        chipViews.forEach { chip ->
            if (chip === selected) {
                chip.setTextColor(Color.WHITE)
                chip.background = ContextCompat.getDrawable(ctx, R.drawable.bg_chip_selected)
            } else {
                chip.setTextColor(primaryColor)
                chip.background = ContextCompat.getDrawable(ctx, R.drawable.bg_chip_unselected)
            }
        }
    }

    private fun applyFilter() {
        val filtered: List<DrugItem> = when {
            selectedCho != null -> CommonDrugs.filterByChoseong(selectedCho!!)
            else -> CommonDrugs.list
        }
        if (filtered.isEmpty()) showEmpty() else showList(filtered)
    }

    private fun showList(items: List<DrugItem>) {
        binding.layoutEmpty.visibility = View.GONE
        binding.recyclerSearch.visibility = View.VISIBLE
        adapter.submitList(items)
    }

    private fun showEmpty() {
        binding.tvEmptyTitle.text = "해당 약이 없어요"
        binding.tvEmptySub.text = "다른 초성이나 이름으로 검색해보세요"
        binding.layoutEmpty.visibility = View.VISIBLE
        binding.recyclerSearch.visibility = View.GONE
    }

    private fun dp(value: Int): Int =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, value.toFloat(),
            resources.displayMetrics
        ).toInt()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
