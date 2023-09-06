package com.example.clickerproject
import retrofit2.Callback
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import retrofit2.Call
import retrofit2.Response
import android.widget.Button


class Activity_register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val buttonRegister: Button = findViewById(R.id.buttonRegister)
        buttonRegister.setOnClickListener {
            val novoUsuario = Usuario(id = 1, nome = "Nome do Usuário", email = "email@example.com", senha = "senha123")
            val call = ApiClient.apiService.criarUsuario(novoUsuario)
            call.enqueue(object : Callback<Usuario> {
                override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                    if (response.isSuccessful) {
                        val usuarioCriado = response.body()
                        
                    } else {
                        Log.e("API Error", "Response Code: ${response.code()}")
                        Log.e("API Error", "Error Body: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<Usuario>, t: Throwable) {
                    Log.e("API Error", "Failed to make the request: ${t.message}")
                }
            })
        }
    }
    //val game = Intent(this, MainActivity::class.java)
   // startActivity(game)

    fun goToLogin(view: View) {
        val intent = Intent(this, Activity_login::class.java)
        startActivity(intent)

    }
}