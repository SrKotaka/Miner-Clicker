package com.example.clickerproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class Activity_login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun loginInDataBase(view: View) {
        val email = findViewById<TextView>(R.id.editTextEmail).text.toString()
        val password = findViewById<TextView>(R.id.editTextPassword).text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(applicationContext, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val request = JSONObject()
        request.put("email", email)
        request.put("password", password)

        val queue: RequestQueue = Volley.newRequestQueue(this)
        val url = "http://192.168.28.186:3000/usuarios"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, request,
            { response ->
                val goToGame = Intent(this, MainActivity::class.java)
                startActivity(goToGame)
            },
            { error ->
                Toast.makeText(applicationContext, "Registration failed: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(jsonObjectRequest)
    }

    fun goToRegister(view: View) {
        try {
            val goToRegister = Intent(this@Activity_login, Activity_register::class.java)
            startActivity(goToRegister)
        } catch (e: Exception){
            Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}