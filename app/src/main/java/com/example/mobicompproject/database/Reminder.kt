package com.example.mobicompproject.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "reminder_table")
data class Reminder (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val message: String,
    val reminder_time: Long,
    val creation_time: Long,
    val location_x: Double? = null,
    val location_y: Double? = null,
    val creator_id: Int,
    val reminder_seen: Boolean
    ): Parcelable