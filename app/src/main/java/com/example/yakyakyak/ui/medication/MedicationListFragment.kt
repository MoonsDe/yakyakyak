package com.example.yakyakyak.ui.medication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yakyakyak.databinding.FragmentMedicationListBinding
import com.example.yakyakyak.viewmodel.MedicationViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MedicationListFragment : Fragment() {

    private var _binding: FragmentMedicationListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MedicationViewModel by viewModels()
    private lateinit var adapter: MedicationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMedicationListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeData()
        setupFab()
    }

    private fun setupRecyclerView() {
        adapter = MedicationAdapter(
            onEdit = { medication ->
                val intent = Intent(requireContext(), AddEditMedicationActivity::class.java)
                intent.putExtra(AddEditMedicationActivity.EXTRA_MEDICATION_ID, medication.id)
                startActivity(intent)
            },
            onDelete = { medication ->
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("약 삭제")
                    .setMessage("'${medication.name}'을 삭제하시겠어요?")
                    .setPositiveButton("삭제") { _, _ ->
                        viewModel.delete(medication)
                    }
                    .setNegativeButton("취소", null)
                    .show()
            }
        )
        binding.recyclerViewMedications.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@MedicationListFragment.adapter
        }
    }

    private fun observeData() {
        viewModel.allMedications.observe(viewLifecycleOwner) { medications ->
            adapter.submitList(medications)
            binding.layoutEmpty.visibility = if (medications.isNullOrEmpty()) View.VISIBLE else View.GONE
            binding.recyclerViewMedications.visibility =
                if (medications.isNullOrEmpty()) View.GONE else View.VISIBLE
        }
    }

    private fun setupFab() {
        binding.fabAddMedication.setOnClickListener {
            val intent = Intent(requireContext(), AddEditMedicationActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
