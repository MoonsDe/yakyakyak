package com.example.yakyakyak.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yakyakyak.data.AppDatabase
import com.example.yakyakyak.data.MedicationLog
import com.example.yakyakyak.databinding.FragmentHomeBinding
import com.example.yakyakyak.repository.MedicationRepository
import com.example.yakyakyak.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: TodayMedicationAdapter
    private lateinit var repository: MedicationRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = MedicationRepository(
            AppDatabase.getDatabase(requireContext()).medicationDao()
        )

        setupDate()
        setupRecyclerView()
        observeData()
    }

    private fun setupDate() {
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 (E)", Locale.KOREAN)
        binding.tvDate.text = today.format(formatter)
    }

    private fun setupRecyclerView() {
        adapter = TodayMedicationAdapter { log ->
            viewModel.toggleTaken(log)
        }
        binding.recyclerViewToday.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@HomeFragment.adapter
        }
    }

    private fun observeData() {
        // 활성화된 약 목록 → 오늘 로그 자동 생성
        viewModel.allActiveMedications.observe(viewLifecycleOwner) { medications ->
            if (!medications.isNullOrEmpty()) {
                lifecycleScope.launch {
                    repository.ensureTodayLogs(medications)
                }
            }
        }

        // 오늘 복용 로그 관찰
        viewModel.todayLogs.observe(viewLifecycleOwner) { logs ->
            adapter.submitList(logs)
            updateEmptyState(logs.isNullOrEmpty())
            updateProgressUI(logs)
        }
    }

    private fun updateEmptyState(isEmpty: Boolean) {
        binding.layoutEmpty.visibility = if (isEmpty) View.VISIBLE else View.GONE
        binding.recyclerViewToday.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }

    private fun updateProgressUI(logs: List<MedicationLog>?) {
        if (logs.isNullOrEmpty()) {
            binding.tvProgress.text = "0 / 0 완료"
            binding.progressBar.progress = 0
            binding.tvProgressMessage.text = "오늘 복용할 약이 없어요"
            binding.tvScheduleCount.text = ""
            return
        }
        val taken = logs.count { it.isTaken }
        val total = logs.size
        val percent = (taken.toFloat() / total * 100).toInt()
        binding.tvProgress.text = "$taken / $total 완료"
        binding.progressBar.progress = percent
        binding.tvScheduleCount.text = "${total}개"
        binding.tvProgressMessage.text = when {
            taken == total -> "오늘 복용 완료! 정말 잘했어요 🎉"
            taken == 0 -> "오늘도 건강하게 시작해요!"
            else -> "조금만 더! ${total - taken}개 남았어요 💪"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
