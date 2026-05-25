package com.example.yakyakyak.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MedicationDao {

    // ──────────── Medication CRUD ────────────

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedication(medication: Medication): Long

    @Update
    suspend fun updateMedication(medication: Medication)

    @Delete
    suspend fun deleteMedication(medication: Medication)

    @Query("SELECT * FROM medications WHERE isActive = 1 ORDER BY name ASC")
    fun getAllActiveMedications(): LiveData<List<Medication>>

    @Query("SELECT * FROM medications ORDER BY name ASC")
    fun getAllMedications(): LiveData<List<Medication>>

    @Query("SELECT * FROM medications WHERE id = :id")
    suspend fun getMedicationById(id: Int): Medication?

    // ──────────── MedicationLog CRUD ────────────

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: MedicationLog): Long

    @Update
    suspend fun updateLog(log: MedicationLog)

    @Query("SELECT * FROM medication_logs WHERE date = :date ORDER BY scheduledTime ASC")
    fun getLogsByDate(date: String): LiveData<List<MedicationLog>>

    @Query("SELECT * FROM medication_logs WHERE medicationId = :medicationId ORDER BY date DESC, scheduledTime ASC")
    fun getLogsByMedication(medicationId: Int): LiveData<List<MedicationLog>>

    @Query("""
        SELECT * FROM medication_logs
        WHERE date = :date AND medicationId = :medicationId AND scheduledTime = :time
    """)
    suspend fun getLog(medicationId: Int, date: String, time: String): MedicationLog?

    @Query("SELECT * FROM medication_logs WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    fun getLogsBetween(startDate: String, endDate: String): LiveData<List<MedicationLog>>
}
