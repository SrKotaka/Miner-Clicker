package com.example.clickerproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class Activity_login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val buttonLogin: Button = findViewById(R.id.buttonLogin)
        buttonLogin.setOnClickListener {

        }
    }

    fun goToRegister(view: View) {
        val register = Intent(this@Activity_login, Activity_register::class.java)
        startActivity(register)

    }
}