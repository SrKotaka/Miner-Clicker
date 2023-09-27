package com.example.clickerproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.google.firebase.firestore.FirebaseFirestore
import android.widget.Toast


class Activity_login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun goToRegister(view: View) {
        try {
            val register = Intent(this@Activity_login, Activity_register::class.java)
            startActivity(register)
        }
        catch (e: Exception){
            Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun loginInDataBase(view: View) {
        val email = findViewById<EditText>(R.id.editTextLoginEmail).text.toString()
        val senha = findViewById<EditText>(R.id.editTextLoginPassword).text.toString()

        val db = FirebaseFirestore.getInstance()
        val usersCollection = db.collection("usuarios")

        // Query the Firestore collection to find a user with the provided email and password
        usersCollection.whereEqualTo("email", email)
            .whereEqualTo("senha", senha)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    // Login successful, user with matching email and password found
                    val game = Intent(this@Activity_login, MainActivity::class.java)
                    startActivity(game)
                } else {
                    // No user found with the provided credentials
                    Toast.makeText(
                        applicationContext,
                        "Seu login não funcionou, por favor tente novamente",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .addOnFailureListener { e ->
                // Handle any errors that occur during the query
                Toast.makeText(
                    applicationContext,
                    "Login falhou, Porfavor tente novamente",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }


}