package com.example.mobicompproject.fragments.update

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
import androidx.navigation.fragment.navArgs
import com.example.mobicompproject.R
import com.example.mobicompproject.database.Reminder
import com.example.mobicompproject.database.ReminderViewModel
import com.example.mobicompproject.databinding.FragmentUpdateBinding
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private lateinit var reminderViewModel: ReminderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)

        //ViewModel
        reminderViewModel = ViewModelProvider(this).get(ReminderViewModel::class.java)



        // Display current reminder details
        binding.updateEtMessage.setText(args.currentReminder.message)

        val reminderTime = args.currentReminder.reminder_time
        val dateTime = LocalDateTime.ofEpochSecond(reminderTime,0, ZoneOffset.UTC)


        val date = "${dateTime.dayOfMonth}.${dateTime.monthValue}.${dateTime.year}"
        val time = "${dateTime.hour}:${dateTime.minute}"

        binding.updateDate.text = date
        binding.updateTime.text = time

        // Set new time and date

        binding.updateDate.setOnClickListener {
            getDate()
        }
        binding.updateTime.setOnClickListener {
            getTime()
        }



        binding.buttonUpdateReminder.setOnClickListener {
            updateReminder()
        }

        return binding.root
    }


    private fun updateReminder() {
        val message = binding.updateEtMessage.text.toString()
        val date = binding.updateDate.text.toString()
        val savedDate:List<String> = date.split(".")
        val day = savedDate[0].toInt()
        val month = savedDate[1].toInt()
        val year = savedDate[2].toInt()
        val savedTime =   binding.updateTime.text.toString().split(":")
        val hour = savedTime[0].toInt()
        val minute = savedTime[1].toInt()
        val localDateTime = LocalDateTime.of(year, month, day, hour, minute)
        val zonedDateTime = localDateTime.atZone(ZoneId.systemDefault())
        val reminderTime = zonedDateTime.toEpochSecond()
        val updatedReminder =
            Reminder(args.currentReminder.id, message, reminderTime, System.currentTimeMillis()/1000, null, null, 0, false)
        reminderViewModel.updateReminder(updatedReminder)
        // Make a toast
        Toast.makeText(requireContext(), "Reminder updated!", Toast.LENGTH_LONG).show()
        // Navigate back to list
        findNavController().navigate(R.id.action_updateFragment_to_listFragment)

    }

    private fun getDate():String {
        val date: LocalDateTime = LocalDateTime.now()
        var savedDate = ""
        val dpd = DatePickerDialog(requireContext(), { _, savedYear, savedMonth, savedDay ->

            // Display Selected date
            savedDate = "${savedDay}.${savedMonth+1}.${savedYear}"
            binding.updateDate.text = savedDate

        }, date.year, date.monthValue-1, date.dayOfMonth)

        dpd.show()

        return savedDate
    }

    private fun getTime():String {
        val date: LocalDateTime = LocalDateTime.now()
        var savedTime = ""
        val dpd = TimePickerDialog(requireContext(),
            { _, savedHour, savedMinute ->
            // Display Selected time
            savedTime = "${savedHour}:${savedMinute}"
            binding.updateTime.text = savedTime

        }, date.hour, date.minute, true)

        dpd.show()
        return savedTime
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}