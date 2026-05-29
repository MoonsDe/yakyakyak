package com.example.yakyakyak.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 약 정보 Entity
 * - times: 쉼표로 구분된 복용 시간 문자열 (예: "08:00,12:00,20:00")
 * - cycle: "매일" | "격일" | "특정 요일"
 * - cycleDays: 특정 요일일 때 사용 (예: "1,3,5" = 월,수,금)
 */
@Entity(tableName = "medications")
data class Medication(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val times: String,           // "08:00,12:00,20:00"
    val cycle: String = "매일",   // "매일" | "격일" | "특정 요일"
    val cycleDays: String = "",  // "1,3,5" (월=1, 화=2, ...)
    val startDate: String,       // "2024-01-01"
    val endDate: String,         // "2024-03-31"
    val memo: String = "",
    val isActive: Boolean = true,
    val mealTiming: String = "없음",  // "없음" | "식전" | "식후 30분" | "식후 1시간" | "식간"
    val cautionText: String = ""      // 성분 정보 (약물 상호작용 체크용)
) {
    fun getTimeList(): List<String> =
        if (times.isBlank()) emptyList()
        else times.split(",").map { it.trim() }

    fun getCycleDayList(): List<Int> =
        if (cycleDays.isBlank()) emptyList()
        else cycleDays.split(",").mapNotNull { it.trim().toIntOrNull() }
}
