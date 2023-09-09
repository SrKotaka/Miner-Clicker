package com.example.clickerproject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

// Define a interface de serviço Retrofit
interface ApiService {
    @POST("usuario") // O endpoint da sua API
    fun cadastrarUsuario(@Body usuario: Usuario): Call<Void>
}

// Inicialize o Retrofit
val retrofit = Retrofit.Builder()
    .baseUrl("http://localhost:1000/api/") // A URL base da sua API
    .addConverterFactory(GsonConverterFactory.create())
    .build()

// Crie uma instância do serviço Retrofit
val apiService = retrofit.create(ApiService::class.java)
