package com.example.yakyakyak.repository

import androidx.lifecycle.LiveData
import com.example.yakyakyak.data.AppDatabase
import com.example.yakyakyak.data.Medication
import com.example.yakyakyak.data.MedicationDao
import com.example.yakyakyak.data.MedicationLog
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MedicationRepository(private val dao: MedicationDao) {

    // ──────────── Medication ────────────

    val allActiveMedications: LiveData<List<Medication>> = dao.getAllActiveMedications()
    val allMedications: LiveData<List<Medication>> = dao.getAllMedications()

    suspend fun insert(medication: Medication) = dao.insertMedication(medication)

    suspend fun update(medication: Medication) = dao.updateMedication(medication)

    suspend fun delete(medication: Medication) = dao.deleteMedication(medication)

    suspend fun getById(id: Int) = dao.getMedicationById(id)

    // ──────────── MedicationLog ────────────

    fun getLogsByDate(date: String): LiveData<List<MedicationLog>> =
        dao.getLogsByDate(date)

    fun getLogsBetween(start: String, end: String): LiveData<List<MedicationLog>> =
        dao.getLogsBetween(start, end)

    /**
     * 오늘 복용 스케줄에 해당하는 로그가 없으면 자동으로 생성
     */
    suspend fun ensureTodayLogs(medications: List<Medication>) {
        val today = LocalDate.now()
        val todayStr = today.format(DateTimeFormatter.ISO_LOCAL_DATE)
        val todayDayOfWeek = today.dayOfWeek.value  // 1=Mon ... 7=Sun

        medications.forEach { med ->
            if (!med.isActive) return@forEach

            // 복용 기간 체크
            val start = LocalDate.parse(med.startDate)
            val end = LocalDate.parse(med.endDate)
            if (today.isBefore(start) || today.isAfter(end)) return@forEach

            // 복용 주기 체크
            val shouldTakeToday = when (med.cycle) {
                "매일" -> true
                "격일" -> {
                    val daysBetween = java.time.temporal.ChronoUnit.DAYS.between(start, today)
                    daysBetween % 2 == 0L
                }
                "특정 요일" -> med.getCycleDayList().contains(todayDayOfWeek)
                else -> true
            }
            if (!shouldTakeToday) return@forEach

            // 각 복용 시간마다 로그 생성
            med.getTimeList().forEach { time ->
                val existing = dao.getLog(med.id, todayStr, time)
                if (existing == null) {
                    dao.insertLog(
                        MedicationLog(
                            medicationId = med.id,
                            medicationName = med.name,
                            date = todayStr,
                            scheduledTime = time,
                            isTaken = false,
                            mealTiming = med.mealTiming
                        )
                    )
                }
            }
        }
    }

    suspend fun updateMealDone(log: MedicationLog) {
        val now = java.time.LocalTime.now()
            .format(DateTimeFormatter.ofPattern("HH:mm"))
        dao.updateLog(log.copy(mealDoneAt = now))
    }

    /**
     * 복용 완료 토글
     */
    suspend fun toggleTaken(log: MedicationLog) {
        val now = java.time.LocalTime.now()
            .format(DateTimeFormatter.ofPattern("HH:mm"))
        dao.updateLog(
            log.copy(
                isTaken = !log.isTaken,
                takenAt = if (!log.isTaken) now else null
            )
        )
    }
}
