package com.example.clickerproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
//From here down I put it
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley

class Activity_rank : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rank)
        nameRank()
        coinsRank()
    }

    private fun nameRank(){
        val rankTextView = findViewById<TextView>(R.id.rank)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val url = "http://192.168.156.70:3000/usuarios"

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val users = StringBuilder()

                    for (i in 0 until response.length()) {
                        val jsonObject = response.getJSONObject(i)
                        val name = jsonObject.getString("name")

                        users.append("$name\n")
                    }

                    rankTextView.text = users.toString()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            },
            { error ->
                Toast.makeText(applicationContext, "Error to pull name: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(jsonArrayRequest)
    } //optimize

    private fun coinsRank(){
        val coinsTextView = findViewById<TextView>(R.id.coins)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val url = "http://192.168.156.70:3000/usuarios"

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val users = StringBuilder()

                    for (i in 0 until response.length()) {
                        val jsonObject = response.getJSONObject(i)
                        val coins = jsonObject.getString("coins")

                        users.append("$coins\n")
                    }

                    coinsTextView.text = users.toString()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            },
            { error ->
                Toast.makeText(applicationContext, "Error to pull coins: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(jsonArrayRequest)
    } //optimize

    fun goToMain(view: View) {
        try {
            val game = Intent(this@Activity_rank, MainActivity::class.java)
            startActivity(game)
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT).show()
        }
    } //optimize

    fun goToAchivements(view: View) {
        try {
            val achivements = Intent(this@Activity_rank, Activity_achivements::class.java)
            startActivity(achivements)
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT).show()
        }
    } //optimize

}