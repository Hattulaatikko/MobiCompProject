package com.example.mobicompproject.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.example.mobicompproject.R
import com.example.mobicompproject.util.sendNotification

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager

        //val message = intent.getStringExtra("ReminderMessage").toString()

        notificationManager.sendNotification(
            context.getString(R.string.notification_message),
            context
        )
    }
}