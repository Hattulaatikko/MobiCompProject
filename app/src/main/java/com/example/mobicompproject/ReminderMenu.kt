package com.example.mobicompproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class ReminderMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reminder_menu)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = ReminderItemAdapter(this, reminders)
        recyclerView.setHasFixedSize(true)
    }

    var reminders: MutableList<Reminder> = mutableListOf(
        Reminder(R.string.reminder1, "13.3.2021", "18:00" , "Golden Scissors"),
        Reminder(R.string.reminder2, "15.4.2021", "12:00", "University of Oulu")
    )
    fun loadReminders(): List<Reminder> {
        return reminders
    }
}