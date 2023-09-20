package com.example.clickerproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser



class Activity_login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun goToRegister(view: View) {
        val register = Intent(this@Activity_login, Activity_register::class.java)
        startActivity(register)
    }

    fun loginToDataBase(view: View) {
        val email = findViewById<EditText>(R.id.editTextEmail).text.toString()
        val senha = findViewById<EditText>(R.id.editTextPassword).text.toString()

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha).addOnCompleteListener { task ->
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
                    Toast.makeText(this@Activity_login, "Falha no login, verifique suas credenciais", Toast.LENGTH_SHORT).show()
                }
            }
    }

}