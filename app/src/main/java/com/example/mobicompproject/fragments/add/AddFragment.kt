package com.example.mobicompproject.fragments.add

import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
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


private val TAG = "AddFragment"

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

        createChannel(getString(R.string.reminder_notification_channel_id), getString(R.string.reminder_notification_channel_name))

        binding.newDate.setOnClickListener {
            getDate()
        }
        binding.newTime.setOnClickListener {
            getTime()
        }
        binding.newLocation.setOnClickListener {
            getLocation()
            Log.d(TAG, "getLocationListener")
        }

        binding.buttonAddReminder.setOnClickListener {
            insertDataToDatabase()
        }



        return binding.root
    }


    private fun insertDataToDatabase() {
        val message = binding.newEtMessage.text.toString()
        val date = reminderViewModel.savedDate
        val time = reminderViewModel.savedTime
        val reminderTime = reminderViewModel.dateTimeStringToEpochSeconds(date, time)
        Log.d(TAG, reminderViewModel.latitude.toString())
        if(reminderViewModel.latitude == 0.0 || reminderViewModel.longitude == 0.0){
            val reminder =
                Reminder(0, message, reminderTime, System.currentTimeMillis()/1000, null, null, 0, false)
            reminderViewModel.addReminder(reminder)
        } else {
            val reminder =
                Reminder(0, message, reminderTime, System.currentTimeMillis()/1000, reminderViewModel.latitude, reminderViewModel.longitude, 0, false)
            reminderViewModel.addReminder(reminder)
        }
        //Log.d("AddFragment", "reminderTime in seconds: $reminderTime")

        if(binding.newCbNotification.isChecked) {
            if (reminderViewModel._alarmOn.value!!) {
                Log.d("AddFragment", "reminderTime in seconds: $reminderTime")
                reminderViewModel.createAlarm(reminderTime * 1000)
            }
        }
        Toast.makeText(requireContext(), "Reminder added!", Toast.LENGTH_LONG).show()
        reminderViewModel.savedDate = ""
        reminderViewModel.savedTime = ""
        findNavController().navigate(R.id.action_addFragment_to_listFragment)

    }

    private fun getDate():String {
        val date: LocalDateTime = LocalDateTime.now()
        var savedDate = ""
        val dpd = DatePickerDialog(requireContext(), { _, savedYear, savedMonth, savedDay ->

            // Display Selected date
            savedDate = "${savedDay}.${savedMonth+1}.${savedYear}"
            reminderViewModel.savedDate = savedDate
            binding.newDate.text = savedDate

        }, date.year, date.monthValue-1, date.dayOfMonth)
        dpd.show()
        return savedDate
    }

    private fun getTime():String {
        val date: LocalDateTime = LocalDateTime.now()
        var savedTime = ""
        val dpd = TimePickerDialog(requireContext(), { _, savedHour, savedMinute ->
            // Display Selected time
            savedTime = "${savedHour}:${savedMinute}"
            reminderViewModel.savedTime = savedTime
            binding.newTime.text = savedTime
        }, date.hour, date.minute, true)
        dpd.show()
        return savedTime
    }

    private fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.BLUE
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Reminder"

            val notificationManager = requireActivity().getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }

    }

    //I can't get the location values back from the dialog
    private fun getLocation() {
        findNavController().navigate(R.id.action_addFragment_to_mapsFragment)
        Log.d(TAG, "getLocationFunction")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}