package com.example.mobicompproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.mobicompproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonNewUser.setOnClickListener { saveUser() }
        binding.buttonLogin.setOnClickListener { logUserIn() }
    }

    private fun saveUser() {
        val username = binding.editTextUsername.text.toString()
        val password = binding.editTextPassword.text.toString()
        Log.d("Username: ", username)
        val sharedPreferences = getSharedPreferences("com.mobicompproject", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(username, password)
        editor.apply()
    }
    private fun logUserIn() {
        val username: String = binding.editTextUsername.text.toString()
        val password: String = binding.editTextPassword.text.toString()
        val sharedPreferences = getSharedPreferences("com.mobicompproject", Context.MODE_PRIVATE)
        if (password == sharedPreferences.getString(username, "wrong")) {
            Log.d("login", "Correct password entered!")
            startActivity(Intent(this, ListActivity::class.java))
        } else {
            Log.d("login", "Incorrect password.")
        }
    }

}
