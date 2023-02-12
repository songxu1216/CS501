package com.example.assignment3

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up the login button click listener
        val loginButton = findViewById<Button>(R.id.login_button)
        loginButton.setOnClickListener {
            // Simulate login with hardcoded credentials (username: "user", password: "password")
            val usernameEditText = findViewById<EditText>(R.id.username_edittext)
            val passwordEditText = findViewById<EditText>(R.id.password_edittext)
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            if (username == "user" && password == "password") {
                val intent = Intent(this, FlashCardActivity::class.java)
                intent.putExtra("username", username) // pass username to FlashCardActivity
                startActivity(intent)
            } else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
