package com.example.clickerproject
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class Activity_register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val buttonRegister: Button = findViewById(R.id.buttonRegister)
        buttonRegister.setOnClickListener {
            val id = 1
            val nome = findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.editTextUsername).text.toString()
            val email = findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.editTextEmail).text.toString()
            val senha = findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.editTextPassword).text.toString()
            val usuario = Usuario( id,nome, email, senha)
            runBlocking {
                launch(Dispatchers.IO) {
                    val response = ApiClient.apiService.criarUsuario(usuario).execute()
                    if (response.isSuccessful) {
                        val usuario = response.body()
                        Log.i("API", usuario.toString())
                    } else {
                        Log.e("API", response.code().toString())
                    }
                }
            }
            val register = Intent(this@Activity_register, Activity_login::class.java)
            startActivity(register)
        }
    }


    fun goToLogin(view: View) {
        val login = Intent(this@Activity_register, Activity_login::class.java)
        startActivity(login)

    }
}