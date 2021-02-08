package com.example.mobicompproject

import androidx.annotation.StringRes

data class Reminder (@StringRes val reminderResourceId: Int, val date: String, val time: String, val location: String)