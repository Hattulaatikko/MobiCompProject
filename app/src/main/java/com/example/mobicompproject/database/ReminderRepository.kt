package com.example.mobicompproject.database

import androidx.lifecycle.LiveData

class ReminderRepository(private val reminderDao: ReminderDao) {

    val readAll: LiveData<List<Reminder>> = reminderDao.readAll()
    val readPast: LiveData<List<Reminder>> = reminderDao.readPast()


    suspend fun addReminder(reminder: Reminder){
        reminderDao.addReminder(reminder)
    }

    suspend fun updateReminder(reminder: Reminder) {
        reminderDao.updateReminder(reminder)
    }

    suspend fun deleteReminder(reminder: Reminder) {
        reminderDao.deleteReminder(reminder)
    }

}