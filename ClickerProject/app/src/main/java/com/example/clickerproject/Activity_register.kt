package com.example.clickerproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.FirebaseApp
import android.widget.Toast

class Activity_register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        FirebaseApp.initializeApp(this)
    }

    fun goToLogin(view: View) {
        try {
            val login = Intent(this@Activity_register, Activity_login::class.java)
            startActivity(login)
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun registerInDataBase(view: View) {
        val nome = findViewById<EditText>(R.id.editTextRegisterName).text.toString()
        val email = findViewById<EditText>(R.id.editTextRegisterEmail).text.toString()
        val senha = findViewById<EditText>(R.id.editTextRegisterPassword).text.toString()

        val db = FirebaseFirestore.getInstance()

        // Create a collection reference and add a new document
        val usersCollection = db.collection("usuarios")
        val userData = hashMapOf(
            "email" to email,
            "nome" to nome,
            "senha" to senha
        )

        usersCollection.add(userData)
            .addOnSuccessListener { documentReference ->
                val game = Intent(this@Activity_register, MainActivity::class.java)
                startActivity(game)
            }
            .addOnFailureListener { e ->
                Toast.makeText(applicationContext, "Seu registro não funcionou, por favor tente novamente", Toast.LENGTH_SHORT).show()
            }

    }

}


