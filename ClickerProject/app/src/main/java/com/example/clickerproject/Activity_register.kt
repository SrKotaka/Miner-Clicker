package com.example.clickerproject
import retrofit2.Callback
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
            val novoUsuario = Usuario(id = 0, nome = "Nome do Usuário", email = "email@example.com", senha = "senha123")

            val call = ApiClient.apiService.criarUsuario(novoUsuario)
            val context = this
            call.enqueue(object : Callback<Usuario> {
                override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                    if (response.isSuccessful) {
                        val usuarioCriado = response.body()
                        val intent = Intent(context, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        // Lidar com erros
                    }
                }

                override fun onFailure(call: Call<Usuario>, t: Throwable) {
                    // Lidar com falhas na comunicação
                }
            })
        }
    }

    fun goToLogin(view: View) {
        val intent = Intent(this, Activity_login::class.java)
        startActivity(intent)

    }
}