package com.example.yakyakyak.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * 약 복용 기록 Entity
 * - medicationId: 어떤 약인지
 * - date: 복용 날짜 "2024-01-01"
 * - scheduledTime: 예정 복용 시간 "08:00"
 * - isTaken: 복용 완료 여부
 * - takenAt: 실제 복용 시간 (nullable)
 */
@Entity(
    tableName = "medication_logs",
    foreignKeys = [ForeignKey(
        entity = Medication::class,
        parentColumns = ["id"],
        childColumns = ["medicationId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("medicationId")]
)
data class MedicationLog(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val medicationId: Int,
    val medicationName: String,
    val date: String,
    val scheduledTime: String,
    val isTaken: Boolean = false,
    val takenAt: String? = null,
    val mealTiming: String = "없음",  // 복용 방법 (Medication에서 복사)
    val mealDoneAt: String? = null    // "식사 완료" 버튼 누른 시각
)
