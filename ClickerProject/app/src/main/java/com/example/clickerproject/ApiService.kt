package com.example.clickerproject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET

interface ApiService {
    @POST("/usuarios")
    fun registerUser(@Body userData: UserData): Call<String>

    @GET("/usuarios")
    fun loginUser(@Body loginData: LoginData): Call<String>
}