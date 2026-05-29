package com.example.yakyakyak.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.yakyakyak.data.AppDatabase
import com.example.yakyakyak.data.MedicationLog
import com.example.yakyakyak.repository.MedicationRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MedicationRepository
    val todayStr: String = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)

    val todayLogs: LiveData<List<MedicationLog>>

    init {
        val dao = AppDatabase.getDatabase(application).medicationDao()
        repository = MedicationRepository(dao)
        todayLogs = repository.getLogsByDate(todayStr)
    }

    /**
     * 앱 시작 시 오늘 복용 스케줄 로그를 자동 생성
     */
    fun ensureTodayLogs() {
        viewModelScope.launch {
            repository.allActiveMedications.value?.let {
                repository.ensureTodayLogs(it)
            }
        }
    }

    /**
     * 활성화된 약 목록을 observe 한 뒤 로그 생성
     */
    fun observeAndEnsureLogs() {
        // HomeFragment 에서 allActiveMedications observe 후 호출
    }

    fun toggleTaken(log: MedicationLog) {
        viewModelScope.launch {
            repository.toggleTaken(log)
        }
    }

    fun setMealDone(log: MedicationLog) {
        viewModelScope.launch {
            repository.updateMealDone(log)
        }
    }

    val allActiveMedications = repository.allActiveMedications
}
