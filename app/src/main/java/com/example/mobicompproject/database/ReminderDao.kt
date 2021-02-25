package com.example.mobicompproject.database

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addReminder(reminder: Reminder)

    @Delete
    suspend fun deleteReminder(reminder: Reminder)

    @Update
    suspend fun updateReminder(reminder: Reminder)

    @Query("SELECT * FROM reminder_table")
    fun readAll(): LiveData<List<Reminder>>
}