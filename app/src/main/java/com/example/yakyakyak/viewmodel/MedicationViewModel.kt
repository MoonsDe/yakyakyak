package com.example.yakyakyak.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.yakyakyak.data.AppDatabase
import com.example.yakyakyak.data.Medication
import com.example.yakyakyak.repository.MedicationRepository
import kotlinx.coroutines.launch

class MedicationViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MedicationRepository

    val allMedications: LiveData<List<Medication>>

    init {
        val dao = AppDatabase.getDatabase(application).medicationDao()
        repository = MedicationRepository(dao)
        allMedications = repository.allMedications
    }

    fun insert(medication: Medication) {
        viewModelScope.launch {
            repository.insert(medication)
        }
    }

    fun update(medication: Medication) {
        viewModelScope.launch {
            repository.update(medication)
        }
    }

    fun delete(medication: Medication) {
        viewModelScope.launch {
            repository.delete(medication)
        }
    }

    fun getById(id: Int, callback: (Medication?) -> Unit) {
        viewModelScope.launch {
            callback(repository.getById(id))
        }
    }
}
