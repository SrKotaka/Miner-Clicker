package com.example.clickerproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth



class Activity_login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun goToRegister(view: View) {
        val register = Intent(this@Activity_login, Activity_register::class.java)
        startActivity(register)
    }

    fun loginInDataBase(view: View) {
        val email = findViewById<EditText>(R.id.editTextEmail).text.toString()
        val senha = findViewById<EditText>(R.id.editTextPassword).text.toString()

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Login bem-sucedido
                    val user = FirebaseAuth.getInstance().currentUser
                    if (user != null) {
                        // O usuário está logado, você pode redirecioná-lo para a tela principal ou realizar outras ações
                        val game = Intent(this@Activity_login, MainActivity::class.java)
                        startActivity(game)
                    }
                } else {
                    // Falha no login
                    if (task.exception != null) {
                        Log.e("LoginError", task.exception.toString())
                    }
                    Toast.makeText(this@Activity_login, "Login falhou.", Toast.LENGTH_SHORT).show()
                }
            }
    }

}