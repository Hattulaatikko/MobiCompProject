package com.example.mobicompproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReminderItemAdapter(private val context: Context, private val reminders: MutableList<Reminder>) : RecyclerView.Adapter<ReminderItemAdapter.ReminderViewHolder>() {

    class ReminderViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.reminder_title)
        val location: TextView = view.findViewById(R.id.reminder_location)
        val date: TextView = view.findViewById(R.id.reminder_date)
        val time: TextView = view.findViewById(R.id.reminder_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        return ReminderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.reminder_item, parent, false))
    }

    //fun addReminder(reminder: Reminder) {
        //TODO
    //}
    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val reminder = reminders[position]
        holder.title.text = context.resources.getString(reminder.reminderResourceId)
        holder.date.text = reminder.date
        holder.time.text = reminder.time
        holder.location.text = reminder.location
    }

    override fun getItemCount() = reminders.size
}