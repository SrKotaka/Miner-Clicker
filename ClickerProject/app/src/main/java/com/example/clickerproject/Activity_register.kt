package com.example.clickerproject
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

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
        val myRef = database.getReference("Users") // Use "Users" como o nó pai para armazenar todos os usuários

        val nome = findViewById<EditText>(R.id.editTextRegisterUsername).text.toString()
        val email = findViewById<EditText>(R.id.editTextRegisterEmail).text.toString()
        val senha = findViewById<EditText>(R.id.editTextRegisterPassword).text.toString()

        // Crie um ID único para cada usuário
        val userId = myRef.push().key

        if (userId != null) {
            val user = HashMap<String, String>()
            user["nome"] = nome
            user["email"] = email
            user["senha"] = senha

            // Adicione o usuário com o ID único ao nó "Users"
            myRef.child(userId).setValue(user)

            val game = Intent(this@Activity_register, MainActivity::class.java)
            startActivity(game)
        }
    }

}


