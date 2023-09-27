package com.example.clickerproject
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Activity_register : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
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
        firestore.collection("usuarios")
            .add(userData)
            .addOnSuccessListener { documentReference ->
                // Document added successfully
                Toast.makeText(this@Activity_register, "User registered successfully", Toast.LENGTH_SHORT).show()
                val game = Intent(this@Activity_register, MainActivity::class.java)
                startActivity(game)
            }
            .addOnFailureListener { e ->
                // Handle errors
                Toast.makeText(this@Activity_register, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }

        //FirebaseAuth.getInstance().createUserWithEmailAndPassword(nome,email, senha)
        //    .addOnCompleteListener { task ->
        //        if (task.isSuccessful) {
        //            val user = FirebaseAuth.getInstance().currentUser
        //            if (user != null) {
        //                val game = Intent(this@Activity_register, MainActivity::class.java)
        //                startActivity(game)
        //            }
        //        } else {
        //            Toast.makeText(this@Activity_register, task.exception.toString(), Toast.LENGTH_SHORT).show()
        //        }
        //}
    }

}


