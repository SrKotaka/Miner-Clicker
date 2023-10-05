package com.example.clickerproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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

        val retrofit = Retrofit.Builder()
            .baseUrl("http://localhost:3000/usuarios") // Replace with your API URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        val loginData = LoginData(email, senha) // Create LoginData object

        val call = apiService.loginUser(loginData)

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    // Login was successful, navigate to MainActivity
                    val game = Intent(this@Activity_login, MainActivity::class.java)
                    startActivity(game)
                } else {
                    // Login failed, show an error message
                    Toast.makeText(
                        applicationContext,
                        "Seu login não funcionou, por favor tente novamente",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                // Request failed, show an error message
                Toast.makeText(
                    applicationContext,
                    "Erro de conexão com o servidor",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }


}