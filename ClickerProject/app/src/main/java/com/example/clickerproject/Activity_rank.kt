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
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class Activity_rank : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rank)

        val rankTextView = findViewById<TextView>(R.id.rank)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val url = "http://192.168.130.80:3000/usuarios"

        val jsonObjectRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val jsonArray = response.getJSONArray(0)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONArray(i).getJSONObject(0)
                        val rank = jsonObject.getString("rank")
                        rankTextView.text = rank
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            },
            { error ->
                Toast.makeText(applicationContext, "Error to pull database: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(jsonObjectRequest)
    }

    fun goToMain(view: View) {
        try {
            val game = Intent(this@Activity_rank, MainActivity::class.java)
            startActivity(game)
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun goToAchivements(view: View) {
        try {
            val achivements = Intent(this@Activity_rank, Activity_achivements::class.java)
            startActivity(achivements)
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}