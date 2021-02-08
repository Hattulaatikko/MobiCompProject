package com.example.mobicompproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.mobicompproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonNewUser.setOnClickListener { saveUser() }
        binding.buttonLogin.setOnClickListener { logUserIn() }
    }

    fun saveUser() {
        val username = binding.editTextUsername.text.toString()
        val password = binding.editTextPassword.text.toString()
        Log.d("Username: ", username.toString())
        val sharedPreferences = getSharedPreferences("com.mobicompproject", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(username.toString(), password.toString())
        editor.apply()
    }
    fun logUserIn() {
        val username: String = binding.editTextUsername.text.toString()
        val password: String = binding.editTextPassword.text.toString()
        val sharedPreferences = getSharedPreferences("com.mobicompproject", Context.MODE_PRIVATE)
        if (password == sharedPreferences.getString(username.toString(), "")) {
            Log.d("login", "Correct password entered!")
            startActivity(Intent(applicationContext, ReminderMenu::class.java))
        } else {
            Log.d("login", "Incorrect password.")
        }

    }
}