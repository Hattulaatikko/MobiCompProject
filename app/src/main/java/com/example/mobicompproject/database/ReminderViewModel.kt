package com.example.mobicompproject.database

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.AlarmManagerCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobicompproject.receiver.AlarmReceiver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId


class ReminderViewModel(application: Application): AndroidViewModel(application) {

    private val REQUEST_CODE = 0

    val readAll: LiveData<List<Reminder>>
    val readPast: LiveData<List<Reminder>>

    private val repository: ReminderRepository

    private val notifyIntent = Intent(getApplication<Application>().applicationContext, AlarmReceiver::class.java)
    private val notifyPendingIntent: PendingIntent
    private val alarmManager = getApplication<Application>().applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    var _alarmOn = MutableLiveData<Boolean>()
    val isAlarmOn: LiveData<Boolean>
        get() = _alarmOn

    init {
        val reminderDao = ReminderDatabase.getDatabase(application).reminderDao()
        repository = ReminderRepository(reminderDao)
        readAll = repository.readAll
        readPast = repository.readPast

        _alarmOn.value = PendingIntent.getBroadcast(
            getApplication<Application>().applicationContext,
            REQUEST_CODE,
            notifyIntent,
            PendingIntent.FLAG_NO_CREATE
        ) != null
        notifyPendingIntent = PendingIntent.getBroadcast(
            getApplication<Application>().applicationContext,
            REQUEST_CODE,
            notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    fun addReminder(reminder: Reminder) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addReminder(reminder)
        }
    }

    fun updateReminder(reminder: Reminder) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateReminder(reminder)
        }
    }

    fun deleteReminder(reminder: Reminder) {
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteReminder(reminder)
        }
    }

    fun createAlarm(alarmTime: Long) {
        Log.d("ViewModel", "CreateAlarm1")
        alarmManager.let {
            AlarmManagerCompat.setExactAndAllowWhileIdle(
                alarmManager,
                AlarmManager.RTC_WAKEUP,
                alarmTime,
                notifyPendingIntent
            )
        }
    }

    fun dateTimeStringToEpochSeconds(date: String, time: String): Long {
        val savedDate:List<String> = date.split(".")
        val day = savedDate[0].toInt()
        val month = savedDate[1].toInt()
        val year = savedDate[2].toInt()
        val savedTime = time.split(":")
        val hour = savedTime[0].toInt()
        val minute = savedTime[1].toInt()
        val localDateTime = LocalDateTime.of(year, month, day, hour, minute)
        val zonedDateTime = localDateTime.atZone(ZoneId.systemDefault())
        return zonedDateTime.toEpochSecond()
    }



}