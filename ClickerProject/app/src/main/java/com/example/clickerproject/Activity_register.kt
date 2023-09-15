package com.example.clickerproject
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        val database = Firebase.database
        val myRef = database.getReference("User")
        val nome = findViewById<EditText>(R.id.editTextUsername).text.toString()
        val email = findViewById<EditText>(R.id.editTextEmail).text.toString()
        val senha = findViewById<EditText>(R.id.editTextPassword).text.toString()
        myRef.setValue("${"nome: " + nome + " \r\n email: " + email + " \r\n senha: " + senha}")
    }
}


