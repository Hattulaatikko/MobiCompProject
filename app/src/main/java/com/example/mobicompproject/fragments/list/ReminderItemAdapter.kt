package com.example.mobicompproject.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mobicompproject.R
import com.example.mobicompproject.database.Reminder
import com.example.mobicompproject.database.ReminderViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ReminderItemAdapter(private val reminderViewModel: ReminderViewModel) : RecyclerView.Adapter<ReminderItemAdapter.ReminderViewHolder>() {

    private var reminderList = emptyList<Reminder>()


    class ReminderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.reminder_title)
        val location: TextView = view.findViewById(R.id.reminder_location)
        val date: TextView = view.findViewById(R.id.reminder_date)
        val time: TextView = view.findViewById(R.id.reminder_time)
        val edit: ImageView = view.findViewById(R.id.reminder_update)
        val delete: TextView = view.findViewById(R.id.reminder_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {

        return ReminderViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.reminder_item,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {

        val reminder = reminderList[position]
        val stringDateTime = reminder.reminder_time
        val dateTime = LocalDateTime.parse(stringDateTime, DateTimeFormatter.ISO_DATE_TIME)


        val date = "${dateTime.dayOfMonth}.${dateTime.monthValue}.${dateTime.year}"
        val time = "${dateTime.hour}:${dateTime.minute}"


        holder.title.text = reminder.message
        holder.date.text = date
        holder.time.text = time
        holder.location.text = "placeholder location"

        holder.edit.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(reminder)
            holder.itemView.findNavController().navigate(action)
        }
        holder.delete.setOnClickListener {
            reminderViewModel.deleteReminder(reminder)
        }
    }

    override fun getItemCount() = reminderList.size

    fun setData(reminder: List<Reminder>) {
        this.reminderList = reminder
        notifyDataSetChanged()
    }
}