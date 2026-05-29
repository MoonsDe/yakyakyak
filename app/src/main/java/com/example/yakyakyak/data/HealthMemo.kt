package com.example.yakyakyak.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "health_memos")
data class HealthMemo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: String,       // "2024-01-01"
    val title: String,
    val content: String,
    val createdAt: String   // "2024-01-01 09:30"
)
