package com.example.clickerproject

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Activity_achivements : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achivements)

        //val achivement1 = findViewById<ImageView>(R.id.achivement1)
        //if (coins >= 1000) {
        //    achivement1.setBackgroundColor(Color.WHITE)
        //} else {
        //    achivement1.setBackgroundColor(Color.GRAY)
        //}
    }

    fun goToMain(view: View) {
        try {
            val game = Intent(this@Activity_achivements, MainActivity::class.java)
            startActivity(game)
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun goToRank(view: View) {
        try {
            val rank = Intent(this@Activity_achivements, Activity_rank::class.java)
            startActivity(rank)
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}