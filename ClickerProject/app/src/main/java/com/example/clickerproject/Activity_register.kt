package com.example.clickerproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
//daqui pra baixo eu que coloquei
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


class Activity_register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    fun registerInDataBase(view: View) {
        val email = findViewById<TextView>(R.id.editTextEmail).text.toString() // Replace with your EditText IDs
        val name = findViewById<TextView>(R.id.editTextName).text.toString() // Replace with your EditText IDs
        val password = findViewById<TextView>(R.id.editTextPassword).text.toString() // Replace with your EditText IDs

        // Check if any field is empty
        if (email.isEmpty() || name.isEmpty() || password.isEmpty()) {
            Toast.makeText(applicationContext, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val request = JSONObject()
        request.put("email", email)
        request.put("name", name)
        request.put("password", password)

        val queue: RequestQueue = Volley.newRequestQueue(this)
        val url = "http://192.168.156.83:3000/usuarios" // Replace with your API URL

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, request,
            { response ->
                // Registration successful, redirect to the login page
                val goToGame = Intent(this, MainActivity::class.java)
                startActivity(goToGame)
            },
            { error ->
                // Registration failed
                Toast.makeText(applicationContext, "Registration failed: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(jsonObjectRequest)
    }

    fun goToLogin(view: View) {
        try {
            val goToLogin = Intent(this@Activity_register, Activity_login::class.java)
            startActivity(goToLogin)
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}