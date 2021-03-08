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

    @Query("SELECT * FROM reminder_table WHERE reminder_time < strftime('%s', 'now') ORDER BY reminder_time DESC")
    fun readPast(): LiveData<List<Reminder>>

    @Query("SELECT * FROM reminder_table ORDER BY reminder_time DESC")
    fun readAll(): LiveData<List<Reminder>>
}