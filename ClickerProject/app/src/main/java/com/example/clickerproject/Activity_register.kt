package com.example.clickerproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class Activity_register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    fun goToLogin(view: View) {
        val login = Intent(this@Activity_register, Activity_login::class.java)
        startActivity(login)
    }

    fun registerInDataBase(view: View) {
        val nome = findViewById<EditText>(R.id.editTextRegisterName).text.toString()
        val email = findViewById<EditText>(R.id.editTextRegisterEmail).text.toString()
        val senha = findViewById<EditText>(R.id.editTextRegisterPassword).text.toString()

        val userData = hashMapOf(
            "name" to nome,
            "email" to email,
            "senha" to senha
        )

    }

}


