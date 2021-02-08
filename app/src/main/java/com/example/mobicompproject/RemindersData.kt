package com.example.mobicompproject

class RemindersData {
    fun loadReminders(): List<Reminder> {
        return listOf<Reminder>(
            Reminder(R.string.reminder1, "13.3.2021", "18:00" , "Golden Scissors"),
            Reminder(R.string.reminder2, "15.4.2021", "12:00", "University of Oulu")
        )
    }
}