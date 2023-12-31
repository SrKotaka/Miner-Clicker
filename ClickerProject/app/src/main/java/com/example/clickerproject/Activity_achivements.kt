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

        val coins = intent.getDoubleExtra("coins", 0.0)

        val achivement1 = findViewById<ImageView>(R.id.achivement1)
        if (coins >= 1000) {
            achivement1.setBackgroundResource(R.drawable.conclued)
        }

        val achivement2 = findViewById<ImageView>(R.id.achivement2)
        if (coins >= 10000) {
            achivement2.setBackgroundResource(R.drawable.conclued)
        }

        val achivement3 = findViewById<ImageView>(R.id.achivement3)
        if (coins >= 100000) {
            achivement3.setBackgroundResource(R.drawable.conclued)
        }

        val achivement4 = findViewById<ImageView>(R.id.achivement4)
        if (coins >= 1000000) {
            achivement4.setBackgroundResource(R.drawable.conclued)
        }

        val achivement5 = findViewById<ImageView>(R.id.achivement5)
        if (coins >= 10000000) {
            achivement5.setBackgroundResource(R.drawable.conclued)
        }

        val arcoirirs = findViewById<ImageView>(R.id.arcoirirs)
        if (coins >= 100000000) {
            arcoirirs.setBackgroundResource(R.drawable.conclued)
        }

        val rosa = findViewById<ImageView>(R.id.rosa)
        if (coins >= 100000000) {
            rosa.setBackgroundResource(R.drawable.conclued)
        }

        val rubi = findViewById<ImageView>(R.id.rubi)
        if (coins >= 100000000) {
            rubi.setBackgroundResource(R.drawable.conclued)
        }

        val turquesa = findViewById<ImageView>(R.id.turquesa)
        if (coins >= 100000000) {
            turquesa.setBackgroundResource(R.drawable.conclued)
        }

    } //optimize

    fun goToMain(view: View) {
        try {
            val game = Intent(this@Activity_achivements, MainActivity::class.java)
            startActivity(game)
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT).show()
        }
    } //optimize

    fun goToRank(view: View) {
        try {
            val rank = Intent(this@Activity_achivements, Activity_rank::class.java)
            startActivity(rank)
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT).show()
        }
    } //optimize
}