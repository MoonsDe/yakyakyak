package com.example.yakyakyak.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yakyakyak.data.AppDatabase
import com.example.yakyakyak.databinding.FragmentHistoryBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: HistoryAdapter
    private var selectedDate: LocalDate = LocalDate.now()
    private val isoFormatter = DateTimeFormatter.ISO_LOCAL_DATE
    private val displayFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 (E)", Locale.KOREAN)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = HistoryAdapter()
        binding.recyclerHistory.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@HistoryFragment.adapter
        }

        binding.btnPrevDay.setOnClickListener {
            selectedDate = selectedDate.minusDays(1)
            loadDate()
        }
        binding.btnNextDay.setOnClickListener {
            selectedDate = selectedDate.plusDays(1)
            loadDate()
        }

        loadDate()
    }

    private fun loadDate() {
        binding.tvSelectedDate.text = selectedDate.format(displayFormatter)
        val dateStr = selectedDate.format(isoFormatter)

        AppDatabase.getDatabase(requireContext())
            .medicationDao()
            .getLogsByDate(dateStr)
            .observe(viewLifecycleOwner) { logs ->
                adapter.submitList(logs)

                val total = logs.size
                val taken = logs.count { it.isTaken }
                val missed = total - taken

                binding.tvStatTotal.text = total.toString()
                binding.tvStatTaken.text = taken.toString()
                binding.tvStatMissed.text = missed.toString()

                binding.layoutEmpty.visibility = if (logs.isEmpty()) View.VISIBLE else View.GONE
                binding.recyclerHistory.visibility = if (logs.isEmpty()) View.GONE else View.VISIBLE
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
