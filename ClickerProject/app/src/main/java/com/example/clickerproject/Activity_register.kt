package com.example.clickerproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
//From here down I put it
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class Activity_register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    fun registerInDataBase(view: View) {
        val email = findViewById<TextView>(R.id.editTextEmail).text.toString()
        val name = findViewById<TextView>(R.id.editTextName).text.toString()
        val password = findViewById<TextView>(R.id.editTextPassword).text.toString()

        if (email.isEmpty() || name.isEmpty() || password.isEmpty()) {
            Toast.makeText(applicationContext, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val request = JSONObject()
        request.put("email", email)
        request.put("name", name)
        request.put("password", password)

        val queue: RequestQueue = Volley.newRequestQueue(this)
        val url = "http://192.168.130.80:3000/usuarios"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, request,
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

    fun goToLogin(view: View) {
        try {
            val goToLogin = Intent(this@Activity_register, Activity_login::class.java)
            startActivity(goToLogin)
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}