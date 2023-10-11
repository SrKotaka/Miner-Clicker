package com.example.clickerproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.google.firebase.FirebaseApp
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
        try {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://localhost:3000/usuarios")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val api = retrofit.create(ApiService::class.java)

            val email = findViewById<EditText>(R.id.editTextEmail).text.toString()
            val nome = findViewById<EditText>(R.id.editTextName).text.toString()
            val senha = findViewById<EditText>(R.id.editTextPassword).text.toString()

            val userData = UserData(email, nome, senha)

            api.registerUser(userData).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        Toast.makeText(applicationContext, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                        val main = Intent(this@Activity_register, MainActivity::class.java)
                        startActivity(main)
                    } else {
                        Toast.makeText(applicationContext, "Erro ao cadastrar usuário!", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(applicationContext, "Erro ao cadastrar usuário!", Toast.LENGTH_SHORT).show()
                }
            })
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

}


