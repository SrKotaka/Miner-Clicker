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
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class Activity_register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    fun registerInDataBase(view: View) {
        val emailView = findViewById<TextView>(R.id.editTextEmail)
        val nameView = findViewById<TextView>(R.id.editTextName)
        val passwordView = findViewById<TextView>(R.id.editTextPassword)

        val email = emailView.text.toString()
        val name = nameView.text.toString()
        val password = passwordView.text.toString()
        val coins = 0.0

        if (email.isEmpty() || name.isEmpty() || password.isEmpty()) {
            showToast("Please fill in all fields")
            return
        }

        val user = JSONObject()
        user.put("email", email)
        user.put("name", name)
        user.put("password", password)
        user.put("coins", coins)

        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.242.222:3000/usuarios"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, user,
            { response ->
                val goToGame = Intent(this, MainActivity::class.java)
                goToGame.putExtra("email", email)
                startActivity(goToGame)
            },
            { error ->
                showToast("Registration failed: ${error.message}")
            }
        )
        queue.add(jsonObjectRequest)
    } //optimize

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    } //optimize

    fun goToLogin(view: View) {
        try {
            val goToLogin = Intent(this@Activity_register, Activity_login::class.java)
            startActivity(goToLogin)
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT).show()
        }
    } //optimize

}