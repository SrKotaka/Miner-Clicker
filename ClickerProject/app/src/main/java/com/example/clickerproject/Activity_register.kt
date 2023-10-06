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
        val nome = findViewById<EditText>(R.id.editTextRegisterName).text.toString()
        val email = findViewById<EditText>(R.id.editTextRegisterEmail).text.toString()
        val senha = findViewById<EditText>(R.id.editTextRegisterPassword).text.toString()

        if (nome.isNotEmpty() && email.isNotEmpty() && senha.isNotEmpty()) {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.137.48:3000/usuarios/") // Replace with your API URL
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService = retrofit.create(ApiService::class.java)

            val userData = UserData(email, nome, senha) // Create UserData object

            val call = apiService.registerUser(userData)

            call.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        // Registration was successful, navigate to MainActivity
                        val game = Intent(this@Activity_register, MainActivity::class.java)
                        startActivity(game)
                    } else {
                        // Registration failed, show an error message
                        Toast.makeText(
                            applicationContext,
                            "Seu registro não funcionou, por favor tente novamente: ${response.message()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    // Request failed, show an error message
                    Toast.makeText(
                        applicationContext,
                        "${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        } else {
            Toast.makeText(
                applicationContext,
                "Preencha todos os campos acima",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}


