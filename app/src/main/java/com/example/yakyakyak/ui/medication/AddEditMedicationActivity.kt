package com.example.yakyakyak.ui.medication

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.yakyakyak.R
import com.example.yakyakyak.data.Medication
import com.example.yakyakyak.databinding.ActivityAddEditMedicationBinding
import com.example.yakyakyak.viewmodel.MedicationViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AddEditMedicationActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MEDICATION_ID = "extra_medication_id"
    }

    private lateinit var binding: ActivityAddEditMedicationBinding
    private val viewModel: MedicationViewModel by viewModels()

    private val selectedTimes = mutableListOf<String>()
    private var editMedication: Medication? = null
    private val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditMedicationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupCycleSpinner()
        setupTimePicker()
        setupDatePickers()
        setupSaveButton()

        // 수정 모드인지 확인
        val medicationId = intent.getIntExtra(EXTRA_MEDICATION_ID, -1)
        if (medicationId != -1) {
            loadMedicationForEdit(medicationId)
        } else {
            // 기본 날짜: 오늘 ~ 한달 뒤
            val today = LocalDate.now()
            binding.etStartDate.setText(today.format(dateFormatter))
            binding.etEndDate.setText(today.plusMonths(1).format(dateFormatter))
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    private fun setupCycleSpinner() {
        val cycles = arrayOf("매일", "격일", "특정 요일")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cycles)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCycle.adapter = adapter

        binding.spinnerCycle.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: android.widget.AdapterView<*>?, view: View?, position: Int, id: Long) {
                binding.layoutCycleDays.visibility =
                    if (cycles[position] == "특정 요일") View.VISIBLE else View.GONE
            }
            override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {}
        }
    }

    private fun setupTimePicker() {
        binding.btnAddTime.setOnClickListener {
            val now = java.time.LocalTime.now()
            TimePickerDialog(
                this,
                { _, hour, minute ->
                    val timeStr = String.format("%02d:%02d", hour, minute)
                    if (!selectedTimes.contains(timeStr)) {
                        selectedTimes.add(timeStr)
                        selectedTimes.sort()
                        updateTimeChips()
                    } else {
                        Toast.makeText(this, "이미 추가된 시간이에요", Toast.LENGTH_SHORT).show()
                    }
                },
                now.hour, now.minute, true
            ).show()
        }
    }

    private fun updateTimeChips() {
        binding.chipGroupTimes.removeAllViews()
        selectedTimes.forEach { time ->
            val chip = com.google.android.material.chip.Chip(this).apply {
                text = time
                isCloseIconVisible = true
                setOnCloseIconClickListener {
                    selectedTimes.remove(time)
                    updateTimeChips()
                }
            }
            binding.chipGroupTimes.addView(chip)
        }
        binding.tvTimesEmpty.visibility = if (selectedTimes.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun setupDatePickers() {
        binding.etStartDate.setOnClickListener { showDatePicker(isStart = true) }
        binding.etEndDate.setOnClickListener { showDatePicker(isStart = false) }
    }

    private fun showDatePicker(isStart: Boolean) {
        val today = LocalDate.now()
        DatePickerDialog(
            this,
            { _, year, month, day ->
                val date = LocalDate.of(year, month + 1, day)
                val dateStr = date.format(dateFormatter)
                if (isStart) binding.etStartDate.setText(dateStr)
                else binding.etEndDate.setText(dateStr)
            },
            today.year, today.monthValue - 1, today.dayOfMonth
        ).show()
    }

    private fun getSelectedCycleDays(): String {
        val days = mutableListOf<Int>()
        if (binding.cbMon.isChecked) days.add(1)
        if (binding.cbTue.isChecked) days.add(2)
        if (binding.cbWed.isChecked) days.add(3)
        if (binding.cbThu.isChecked) days.add(4)
        if (binding.cbFri.isChecked) days.add(5)
        if (binding.cbSat.isChecked) days.add(6)
        if (binding.cbSun.isChecked) days.add(7)
        return days.joinToString(",")
    }

    private fun setupSaveButton() {
        binding.btnSave.setOnClickListener {
            if (!validateInput()) return@setOnClickListener

            val medication = Medication(
                id = editMedication?.id ?: 0,
                name = binding.etName.text.toString().trim(),
                times = selectedTimes.joinToString(","),
                cycle = binding.spinnerCycle.selectedItem.toString(),
                cycleDays = if (binding.spinnerCycle.selectedItem.toString() == "특정 요일")
                    getSelectedCycleDays() else "",
                startDate = binding.etStartDate.text.toString(),
                endDate = binding.etEndDate.text.toString(),
                memo = binding.etMemo.text.toString().trim(),
                isActive = true
            )

            if (editMedication != null) {
                viewModel.update(medication)
                Toast.makeText(this, "약 정보가 수정되었어요", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.insert(medication)
                Toast.makeText(this, "약이 등록되었어요 💊", Toast.LENGTH_SHORT).show()
            }
            finish()
        }
    }

    private fun validateInput(): Boolean {
        if (binding.etName.text.isNullOrBlank()) {
            binding.tilName.error = "약 이름을 입력해주세요"
            return false
        }
        binding.tilName.error = null

        if (selectedTimes.isEmpty()) {
            Toast.makeText(this, "복용 시간을 1개 이상 추가해주세요", Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.etStartDate.text.isNullOrBlank()) {
            Toast.makeText(this, "시작 날짜를 입력해주세요", Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.etEndDate.text.isNullOrBlank()) {
            Toast.makeText(this, "종료 날짜를 입력해주세요", Toast.LENGTH_SHORT).show()
            return false
        }

        val start = LocalDate.parse(binding.etStartDate.text.toString())
        val end = LocalDate.parse(binding.etEndDate.text.toString())
        if (end.isBefore(start)) {
            Toast.makeText(this, "종료 날짜는 시작 날짜 이후여야 해요", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun loadMedicationForEdit(id: Int) {
        supportActionBar?.title = "약 수정"
        viewModel.getById(id) { medication ->
            medication ?: return@getById
            editMedication = medication
            runOnUiThread {
                binding.etName.setText(medication.name)
                selectedTimes.clear()
                selectedTimes.addAll(medication.getTimeList())
                updateTimeChips()

                val cycleIndex = listOf("매일", "격일", "특정 요일").indexOf(medication.cycle)
                if (cycleIndex >= 0) binding.spinnerCycle.setSelection(cycleIndex)

                if (medication.cycle == "특정 요일") {
                    val days = medication.getCycleDayList()
                    binding.cbMon.isChecked = days.contains(1)
                    binding.cbTue.isChecked = days.contains(2)
                    binding.cbWed.isChecked = days.contains(3)
                    binding.cbThu.isChecked = days.contains(4)
                    binding.cbFri.isChecked = days.contains(5)
                    binding.cbSat.isChecked = days.contains(6)
                    binding.cbSun.isChecked = days.contains(7)
                }

                binding.etStartDate.setText(medication.startDate)
                binding.etEndDate.setText(medication.endDate)
                binding.etMemo.setText(medication.memo)
            }
        }
    }
}
