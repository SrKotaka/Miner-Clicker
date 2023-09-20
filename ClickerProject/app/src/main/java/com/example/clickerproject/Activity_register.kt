package com.example.clickerproject
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth


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
        val email = findViewById<EditText>(R.id.editTextRegisterEmail).text.toString()
        val senha = findViewById<EditText>(R.id.editTextRegisterPassword).text.toString()

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser
                    if (user != null) {
                        val game = Intent(this@Activity_register, MainActivity::class.java)
                        startActivity(game)
                    }
                } else {
                    Toast.makeText(this@Activity_register, task.exception.toString(), Toast.LENGTH_SHORT).show()
                }
        }
    }

}


