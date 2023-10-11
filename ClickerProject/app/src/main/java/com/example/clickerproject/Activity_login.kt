package com.example.clickerproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth


class Activity_login : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()
    }

    fun loginInDataBase(view: View) {
        val email = findViewById<TextView>(R.id.editTextEmail).toString() // Replace with the email input field value
        val password =  findViewById<TextView>(R.id.editTextPassword).toString()// Replace with the password input field value

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI or navigate to another activity
                    val user = mAuth.currentUser
                    // Example: navigate to another activity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        applicationContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun goToRegister(view: View) {
        try {
            val goToRegister = Intent(this@Activity_login, Activity_register::class.java)
            startActivity(goToRegister)
        }
        catch (e: Exception){
            Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

}