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
        val emailView = findViewById<TextView>(R.id.editTextEmail)
        val passwordView = findViewById<TextView>(R.id.editTextPassword)
        val email = emailView.text.toString()
        val password = passwordView.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            showToast("Please fill in all fields")
            return
        }

        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.156.70:3000/usuarios/$email"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val correctPassword = response.optString("password")
                if (password == correctPassword) {
                    val goToGame = Intent(this, MainActivity::class.java)
                    goToGame.putExtra("email", email)
                    startActivity(goToGame)
                } else {
                    showToast("Wrong password")
                }
            },
            { error ->
                showToast("Invalid email")
            }
        )
        queue.add(jsonObjectRequest)
    } //optimize

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    } //optimize

    fun goToRegister(view: View) {
        try {
            val goToRegister = Intent(this@Activity_login, Activity_register::class.java)
            startActivity(goToRegister)
        } catch (e: Exception){
            Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT).show()
        }
    } //optimize
}