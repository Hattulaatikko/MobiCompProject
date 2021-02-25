package com.example.mobicompproject.fragments.add

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mobicompproject.R
import com.example.mobicompproject.database.Reminder
import com.example.mobicompproject.database.ReminderViewModel
import com.example.mobicompproject.databinding.FragmentAddBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private lateinit var reminderViewModel: ReminderViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(layoutInflater, container, false)

        reminderViewModel = ViewModelProvider(this).get(ReminderViewModel::class.java)

        binding.newDate.setOnClickListener {
            getDate()
        }
        binding.newTime.setOnClickListener {
            getTime()
        }

        binding.buttonAddReminder.setOnClickListener {
            insertDataToDatabase()
        }

        return binding.root
    }


    private fun insertDataToDatabase() {
        val message = binding.newEtMessage.text.toString()
        val date = binding.newDate.text.toString()
        val savedDate:List<String> = date.split(".")
        val day = savedDate[0].toInt()
        val month = savedDate[1].toInt()
        val year = savedDate[2].toInt()
        val savedTime =   binding.newTime.text.toString().split(":")
        val hour = savedTime[0].toInt()
        val minute = savedTime[1].toInt()
        val unformattedTime = LocalDateTime.of(year, month, day, hour, minute)
        val reminderTime = unformattedTime.format(DateTimeFormatter.ISO_DATE_TIME)
        val reminder =
            Reminder(0, message, reminderTime, System.currentTimeMillis(), null, null, 0, false)
        reminderViewModel.addReminder(reminder)
        Toast.makeText(requireContext(), "Reminder added!", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_addFragment_to_listFragment)

    }

    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0

    private fun getDateTimeCalendar() {
        val date: LocalDateTime = LocalDateTime.now()
        day = date.dayOfMonth
        month = date.monthValue
        year = date.year
        hour = date.hour
        minute = date.minute
    }

    private fun getDate():String {
        getDateTimeCalendar()
        var savedDate = ""
        val dpd = DatePickerDialog(requireContext(), { _, savedYear, savedMonth, savedDay ->

            // Display Selected date
            savedDate = "${savedDay}.${savedMonth+1}.${savedYear}"
            binding.newDate.text = savedDate

        }, year, month-1, day)

        dpd.show()

        return savedDate
    }

    private fun getTime():String {
        getDateTimeCalendar()
        var savedTime = ""
        val dpd = TimePickerDialog(requireContext(), { _, savedHour, savedMinute ->
            // Display Selected time
            savedTime = "${savedHour}:${savedMinute}"
            binding.newTime.text = savedTime

        }, hour, minute, true)

        dpd.show()
        return savedTime
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}