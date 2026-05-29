package com.example.yakyakyak.ui.memo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yakyakyak.data.AppDatabase
import com.example.yakyakyak.data.HealthMemo
import com.example.yakyakyak.databinding.DialogAddMemoBinding
import com.example.yakyakyak.databinding.FragmentMemoBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MemoFragment : Fragment() {

    private var _binding: FragmentMemoBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MemoAdapter
    private val dao by lazy { AppDatabase.getDatabase(requireContext()).healthMemoDao() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMemoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MemoAdapter(
            onDelete = { memo ->
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("메모 삭제")
                    .setMessage("'${memo.title}' 메모를 삭제할까요?")
                    .setPositiveButton("삭제") { _, _ ->
                        lifecycleScope.launch { dao.delete(memo) }
                    }
                    .setNegativeButton("취소", null)
                    .show()
            },
            onClick = { memo -> showEditDialog(memo) }
        )

        binding.recyclerMemo.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@MemoFragment.adapter
        }

        dao.getAll().observe(viewLifecycleOwner) { memos ->
            adapter.submitList(memos)
            binding.layoutEmpty.visibility = if (memos.isEmpty()) View.VISIBLE else View.GONE
            binding.recyclerMemo.visibility = if (memos.isEmpty()) View.GONE else View.VISIBLE
        }

        binding.fabAddMemo.setOnClickListener { showAddDialog() }
    }

    private fun showAddDialog() {
        val dialogBinding = DialogAddMemoBinding.inflate(layoutInflater)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("건강 메모 작성")
            .setView(dialogBinding.root)
            .setPositiveButton("저장") { _, _ ->
                val title = dialogBinding.etMemoTitle.text.toString().trim()
                val content = dialogBinding.etMemoContent.text.toString().trim()
                if (title.isBlank()) {
                    Toast.makeText(requireContext(), "제목을 입력해주세요", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                val now = LocalDateTime.now()
                val memo = HealthMemo(
                    date = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE),
                    title = title,
                    content = content,
                    createdAt = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                )
                lifecycleScope.launch { dao.insert(memo) }
            }
            .setNegativeButton("취소", null)
            .show()
    }

    private fun showEditDialog(memo: HealthMemo) {
        val dialogBinding = DialogAddMemoBinding.inflate(layoutInflater)
        dialogBinding.etMemoTitle.setText(memo.title)
        dialogBinding.etMemoContent.setText(memo.content)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("메모 수정")
            .setView(dialogBinding.root)
            .setPositiveButton("저장") { _, _ ->
                val title = dialogBinding.etMemoTitle.text.toString().trim()
                val content = dialogBinding.etMemoContent.text.toString().trim()
                if (title.isBlank()) return@setPositiveButton
                lifecycleScope.launch {
                    dao.update(memo.copy(title = title, content = content))
                }
            }
            .setNegativeButton("취소", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
