package com.example.yakyakyak.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface HealthMemoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(memo: HealthMemo): Long

    @Update
    suspend fun update(memo: HealthMemo)

    @Delete
    suspend fun delete(memo: HealthMemo)

    @Query("SELECT * FROM health_memos ORDER BY date DESC, createdAt DESC")
    fun getAll(): LiveData<List<HealthMemo>>

    @Query("SELECT * FROM health_memos WHERE date = :date ORDER BY createdAt DESC")
    fun getByDate(date: String): LiveData<List<HealthMemo>>
}
