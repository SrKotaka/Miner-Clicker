package com.example.clickerproject
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Activity_register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val buttonRegister: Button = findViewById(R.id.buttonRegister)
        buttonRegister.setOnClickListener {
            val id = 1
            val nomeDigitado = findViewById<EditText>(R.id.editTextUsername).toString()
            val emailDigitado = findViewById<EditText>(R.id.editTextEmail).toString()
            val senhaDigitado = findViewById<EditText>(R.id.editTextPassword).toString()
            val usuario = Usuario(id,nomeDigitado, emailDigitado, senhaDigitado)
            val call = apiService.cadastrarUsuario(usuario)
            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        val game = Intent(this@Activity_register, MainActivity::class.java)
                        startActivity(game)
                    } else {
                        Log.e("onResponse error", response.code().toString())
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e("onFailure error", t.message.toString())
                }
            })
        }
    }

    fun goToLogin(view: View) {
        val login = Intent(this@Activity_register, Activity_login::class.java)
        startActivity(login)

    }
}