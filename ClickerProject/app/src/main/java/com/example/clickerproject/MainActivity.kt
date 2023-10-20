package com.example.clickerproject

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var coinsPerSecondTextView: TextView
    private lateinit var upgrade1CoinsPerSecondTextView: TextView
    private lateinit var upgrade4CoinsPerSecondTextView: TextView
    private lateinit var upgrade5CoinsPerSecondTextView: TextView
    private lateinit var upgrade6CoinsPerSecondTextView: TextView
    private var coins = 0.0
    private var mediaPlayer: MediaPlayer? = null
    private var upgrade1Cost = 5.0
    private var upgrade2Cost = 50.0
    private var upgrade3Cost = 2.0
    private var upgrade4Cost = 50000.0
    private var upgrade5Cost = 250000.0
    private var upgrade6Cost = 1000000.0
    private var coinsPerSecond = 0.0
    private var upgrade1CoinsPerSecond = 2.0
    private var upgrade4CoinsPerSecond = 100.0
    private var upgrade5CoinsPerSecond = 25000.0
    private var upgrade6CoinsPerSecond = 100000.0
    private var powerClick = 1.0
    private var soulscoins = 0.0
    private var soulscoinshave = 0.0
    private val hitSoundMap = mutableMapOf<Int, MediaPlayer>()
    private val handler = Handler()
    private var currentMinerioState = "minerio1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        coinsPerSecondTextView = findViewById(R.id.coins_per_second)
        upgrade1CoinsPerSecondTextView = findViewById(R.id.upgrade1_coins_per_second)
        upgrade4CoinsPerSecondTextView = findViewById(R.id.upgrade4_coins_per_second)
        upgrade5CoinsPerSecondTextView = findViewById(R.id.upgrade5_coins_per_second)
        upgrade6CoinsPerSecondTextView = findViewById(R.id.upgrade6_coins_per_second)

        val minerioImageView: ImageView = findViewById(R.id.minerio1)
        minerioImageView.tag = "minerio1"
        minerioImageView.setOnClickListener {

            val bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce_animation)
            minerioImageView.startAnimation(bounceAnimation)

            coins += powerClick

            val hitSound = MediaPlayer.create(this, R.raw.hit)
            hitSound.setOnCompletionListener {
                hitSound.release()
                hitSoundMap.remove(it.hashCode())
            }
            hitSound.start()
            hitSoundMap[hitSound.hashCode()] = hitSound

            updateUI()
        }

        val resetButton: Button = findViewById(R.id.reset_game)
        resetButton.setOnClickListener {
            resetGame()
        }


        val upgradeButton: Button = findViewById(R.id.btn_upgrade1)
        upgradeButton.setOnClickListener {
            if (coins >= upgrade1Cost) {
                coins -= upgrade1Cost
                coinsPerSecond += upgrade1CoinsPerSecond
                upgrade1CoinsPerSecond *= 1.1
                upgrade1Cost = (upgrade1Cost * 2) + 5
                upgradeButton.text = "Upgrade Cost : ${formatDoubleNumber(upgrade1Cost)}"
                updateUI()
            }
        }

        val upgrade2Multiplier = 2
        val upgradeButton2: Button = findViewById(R.id.btn_upgrade2)
        upgradeButton2.setOnClickListener {
            if (coins >= upgrade2Cost) {
                coins -= upgrade2Cost
                coinsPerSecond *= upgrade2Multiplier
                upgrade2Cost = (upgrade2Cost * 5) + 50
                upgrade1CoinsPerSecond *= 2
                upgrade4CoinsPerSecond *= 2
                upgrade5CoinsPerSecond *= 2
                upgrade6CoinsPerSecond *= 2
                powerClick *= 2
                upgradeButton2.text = "Upgrade Cost : ${formatDoubleNumber(upgrade2Cost)}"
                when (minerioImageView.tag) {
                    "minerio1" -> {
                        minerioImageView.setImageResource(R.drawable.minerio2)
                        minerioImageView.tag = "minerio2"
                        currentMinerioState = "minerio2"
                    }

                    "minerio2" -> {
                        minerioImageView.setImageResource(R.drawable.minerio3)
                        minerioImageView.tag = "minerio3"
                        currentMinerioState = "minerio3"
                    }

                    "minerio3" -> {
                        minerioImageView.setImageResource(R.drawable.minerio4)
                        minerioImageView.tag = "minerio4"
                        currentMinerioState = "minerio4"
                    }

                    "minerio4" -> {
                        minerioImageView.setImageResource(R.drawable.minerio5)
                        minerioImageView.tag = "minerio5"
                        currentMinerioState = "minerio5"
                    }

                    "minerio5" -> {
                        minerioImageView.setImageResource(R.drawable.minerio6)
                        minerioImageView.tag = "minerio6"
                        currentMinerioState = "minerio6"
                    }

                    "minerio6" -> {
                        minerioImageView.setImageResource(R.drawable.minerio7)
                        minerioImageView.tag = "minerio7"
                        currentMinerioState = "minerio7"
                    }

                    "minerio7" -> {
                        minerioImageView.setImageResource(R.drawable.minerio8)
                        minerioImageView.tag = "minerio8"
                        currentMinerioState = "minerio8"
                    }

                    "minerio8" -> {
                        minerioImageView.setImageResource(R.drawable.minerio9)
                        minerioImageView.tag = "minerio9"
                        currentMinerioState = "minerio9"
                    }

                    "minerio9" -> {
                        minerioImageView.setImageResource(R.drawable.minerio10)
                        minerioImageView.tag = "minerio10"
                        currentMinerioState = "minerio10"
                        minerioImageView.setImageResource(R.drawable.minerio11)
                    }

                    "minerio10" -> {
                        minerioImageView.tag = "minerio11"
                        currentMinerioState = "minerio11"
                    }

                    "minerio11" -> {
                        minerioImageView.setImageResource(R.drawable.minerio12)
                        minerioImageView.tag = "minerio12"
                        currentMinerioState = "minerio12"
                    }

                    "minerio12" -> {
                        minerioImageView.setImageResource(R.drawable.minerio13)
                        minerioImageView.tag = "minerio13"
                        currentMinerioState = "minerio13"
                    }

                    "minerio13" -> {
                        minerioImageView.setImageResource(R.drawable.minerio14)
                        minerioImageView.tag = "minerio14"
                        currentMinerioState = "minerio14"
                    }

                    "minerio14" -> {
                        minerioImageView.setImageResource(R.drawable.minerio15)
                        minerioImageView.tag = "minerio15"
                        currentMinerioState = "minerio15"
                    }

                    "minerio15" -> {
                        minerioImageView.setImageResource(R.drawable.minerio16)
                        minerioImageView.tag = "minerio16"
                        currentMinerioState = "minerio16"
                    }
                }
                updateUI()
            }

        }

        val upgradeButton3: Button = findViewById(R.id.btn_upgrade3)
        upgradeButton3.setOnClickListener {
            if (coins >= upgrade3Cost) {
                coins -= upgrade3Cost
                upgrade3Cost = (upgrade3Cost * 2) + 2
                powerClick *= 1.1
                upgradeButton3.text = "Upgrade Cost : ${formatDoubleNumber(upgrade3Cost)}"
                updateUI()
            }
        }

        val upgradeButton4: Button = findViewById(R.id.btn_upgrade4)
        upgradeButton4.setOnClickListener {
            if (coins >= upgrade4Cost) {
                coins -= upgrade4Cost
                coinsPerSecond += upgrade4CoinsPerSecond
                upgrade4CoinsPerSecond *= 1.1
                upgrade4Cost = (upgrade4Cost * 2) + 5
                upgradeButton4.text = "Upgrade Cost : ${formatDoubleNumber(upgrade4Cost)}"
                updateUI()
            }
        }

        val upgradeButton5: Button = findViewById(R.id.btn_upgrade5)
        upgradeButton5.setOnClickListener {
            if (coins >= upgrade5Cost) {
                coins -= upgrade5Cost
                coinsPerSecond += upgrade5CoinsPerSecond
                upgrade5CoinsPerSecond *= 1.1
                upgrade5Cost = (upgrade5Cost * 2) + 5
                upgradeButton5.text = "Upgrade Cost : ${formatDoubleNumber(upgrade5Cost)}"
                updateUI()
            }
        }

        val upgradeButton6: Button = findViewById(R.id.btn_upgrade6)
        upgradeButton6.setOnClickListener {
            if (coins >= upgrade6Cost) {
                coins -= upgrade6Cost
                coinsPerSecond += upgrade6CoinsPerSecond
                upgrade6CoinsPerSecond *= 1.1
                upgrade6Cost = (upgrade6Cost * 2) + 5
                upgradeButton6.text = "Upgrade Cost : ${formatDoubleNumber(upgrade6Cost)}"
                updateUI()
            }
        }

        if (isNatal()) {
            createSnowfall()
        }

        updateUI()

        mediaPlayer = MediaPlayer.create(this, R.raw.musica)
        mediaPlayer?.setVolume(0.1f, 0.1f)
        mediaPlayer?.isLooping = true
        mediaPlayer?.start()

        handler.post(updateCoinsPerSecondTask)
        handler.post(updateAPIperMinute)
    }

    private val updateCoinsPerSecondTask = object : Runnable {
        override fun run() {
            coins += coinsPerSecond
            updateUI()
            handler.postDelayed(this, 1000)
        }
    }
    private val updateAPIperMinute = object : Runnable {
        override fun run() {
            updateAPI()
            handler.postDelayed(this, 10000) //5 minutes
        }
    }

    private fun createSnowfall() {

    }

    private fun updateAPI(){
        val email = intent.getStringExtra("email")
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val url = "http://192.168.16.127:3000/usuarios/$email"

        val request = JSONObject()
        request.put("email", email)
        request.put("coins", coins)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.PUT, url, request,
            { response ->
                Toast.makeText(applicationContext, "Coins updated", Toast.LENGTH_SHORT).show()
            },
            { error ->

            }
        )
        queue.add(jsonObjectRequest)
    }

    private fun updateUI() {
        findViewById<TextView>(R.id.coins).text = "Coins : ${formatDoubleNumber(coins)}"
        coinsPerSecondTextView.text = "Coins per second : ${formatDoubleNumber(coinsPerSecond)}"
        upgrade1CoinsPerSecondTextView.text = "Coins per second : ${formatDoubleNumber(upgrade1CoinsPerSecond)}"
        upgrade4CoinsPerSecondTextView.text = "Coins per second : ${formatDoubleNumber(upgrade4CoinsPerSecond)}"
        upgrade5CoinsPerSecondTextView.text = "Coins per second : ${formatDoubleNumber(upgrade5CoinsPerSecond)}"
        upgrade6CoinsPerSecondTextView.text = "Coins per second : ${formatDoubleNumber(upgrade6CoinsPerSecond)}"
        val soulsCoinsTextView = findViewById<TextView>(R.id.souls_coins)
        soulsCoinsTextView.text = "Souls Coins : ${formatDoubleNumber(soulscoins)}"

        val upgradeButton: Button = findViewById(R.id.btn_upgrade1)
        if (coins >= upgrade1Cost) {
            upgradeButton.isEnabled = true
            upgradeButton.setBackgroundColor(Color.WHITE)
        } else {
            upgradeButton.isEnabled = false
            upgradeButton.setBackgroundColor(Color.GRAY)
        }

        val upgradeButton2: Button = findViewById(R.id.btn_upgrade2)
        if (coins >= upgrade2Cost) {
            upgradeButton2.isEnabled = true
            upgradeButton2.setBackgroundColor(Color.WHITE)
        } else {
            upgradeButton2.isEnabled = false
            upgradeButton2.setBackgroundColor(Color.GRAY)
        }

        val upgradeButton3: Button = findViewById(R.id.btn_upgrade3)
        if (coins >= upgrade3Cost) {
            upgradeButton3.isEnabled = true
            upgradeButton3.setBackgroundColor(Color.WHITE)
        } else {
            upgradeButton3.isEnabled = false
            upgradeButton3.setBackgroundColor(Color.GRAY)
        }

        val upgradeButton4: Button = findViewById(R.id.btn_upgrade4)
        if (coins >= upgrade4Cost) {
            upgradeButton4.isEnabled = true
            upgradeButton4.setBackgroundColor(Color.WHITE)
        } else {
            upgradeButton4.isEnabled = false
            upgradeButton4.setBackgroundColor(Color.GRAY)
        }
        val upgradeButton5: Button = findViewById(R.id.btn_upgrade5)
        if (coins >= upgrade5Cost) {
            upgradeButton5.isEnabled = true
            upgradeButton5.setBackgroundColor(Color.WHITE)
        } else {
            upgradeButton5.isEnabled = false
            upgradeButton5.setBackgroundColor(Color.GRAY)
        }
        val upgradeButton6: Button = findViewById(R.id.btn_upgrade6)
        if (coins >= upgrade6Cost) {
            upgradeButton6.isEnabled = true
            upgradeButton6.setBackgroundColor(Color.WHITE)
        } else {
            upgradeButton6.isEnabled = false
            upgradeButton6.setBackgroundColor(Color.GRAY)
        }
        val resetButton: Button = findViewById(R.id.reset_game)
        if (soulscoins >= 1) {
            resetButton.isEnabled = true
            resetButton.setBackgroundColor(Color.WHITE)
        } else {
            resetButton.isEnabled = false
            resetButton.setBackgroundColor(Color.GRAY)
        }
    }

    private fun resetGame() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Reset Game")
        alertDialogBuilder.setMessage("Are you sure you want to reset the game? Your coins will be lost but your upgrades will increase by 10% per soul coin.")
        alertDialogBuilder.setPositiveButton("Reset") { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
            performGameReset()
        }
        alertDialogBuilder.setNegativeButton("Cancel") { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun performGameReset() {
        coins = 0.0
        soulscoinshave += soulscoins
        var bonus = soulscoinshave * 1.1
        coinsPerSecond = 0.0
        upgrade1CoinsPerSecond = 2.0 * bonus
        upgrade4CoinsPerSecond = 100.0 * bonus
        upgrade5CoinsPerSecond = 25000.0 * bonus
        upgrade6CoinsPerSecond = 100000.0 * bonus
        upgrade1Cost = 5.0
        upgrade2Cost = 50.0
        upgrade3Cost = 2.0
        upgrade4Cost = 50000.0
        upgrade5Cost = 250000.0
        upgrade6Cost = 1000000.0
        powerClick = 1.0
        soulscoins = 0.0

        updateUI()

        handler.removeCallbacks(updateCoinsPerSecondTask)

        val prefs = getSharedPreferences("game_prefs", Context.MODE_PRIVATE)
        prefs.edit().clear().apply()

        val minerioImageView: ImageView = findViewById(R.id.minerio1)
        minerioImageView.setImageResource(R.drawable.minerio1)
        minerioImageView.tag = "minerio1"
        currentMinerioState = "minerio1"

        val upgradeButton: Button = findViewById(R.id.btn_upgrade1)
        upgradeButton.text = "Upgrade Cost : ${formatDoubleNumber(upgrade1Cost)}"

        val upgradeButton2: Button = findViewById(R.id.btn_upgrade2)
        upgradeButton2.text = "Upgrade Cost : ${formatDoubleNumber(upgrade2Cost)}"

        val upgradeButton3: Button = findViewById(R.id.btn_upgrade3)
        upgradeButton3.text = "Upgrade Cost : ${formatDoubleNumber(upgrade3Cost)}"

        val upgradeButton4: Button = findViewById(R.id.btn_upgrade4)
        upgradeButton4.text = "Upgrade Cost : ${formatDoubleNumber(upgrade4Cost)}"

        val upgradeButton5: Button = findViewById(R.id.btn_upgrade5)
        upgradeButton5.text = "Upgrade Cost : ${formatDoubleNumber(upgrade5Cost)}"

        val upgradeButton6: Button = findViewById(R.id.btn_upgrade6)
        upgradeButton6.text = "Upgrade Cost : ${formatDoubleNumber(upgrade6Cost)}"

        val soulsCoinsHaveTextView = findViewById<TextView>(R.id.souls_coins_have)
        soulsCoinsHaveTextView.text = "Souls Coins : ${formatDoubleNumber(soulscoinshave)}"

        val soulsCoinsTextView = findViewById<TextView>(R.id.souls_coins)
        soulsCoinsTextView.text = "Souls Coins : ${formatDoubleNumber(soulscoins)}"

        handler.post(updateCoinsPerSecondTask)
    }

    private fun formatDoubleNumber(number: Double): String {
        return when {
            number < 1000 -> String.format("%.0f", number)
            number < 1_000_000 -> String.format("%.1fk", number / 1000.0)
            number < 1_000_000_000 -> String.format("%.1fM", number / 1_000_000.0)
            number < 1_000_000_000_000 -> String.format("%.1fB", number / 1_000_000_000.0)
            number < 1_000_000_000_000_000 -> String.format("%.1fT", number / 1_000_000_000_000.0)
            else -> String.format("%.1e", number)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        handler.removeCallbacks(updateCoinsPerSecondTask)
        hitSoundMap.values.forEach {
            it.release()
        }
    }

    override fun onPause() {
        super.onPause()

        val prefs = getSharedPreferences("game_prefs", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putFloat("coins", coins.toFloat())
        editor.putFloat("coinsPerSecond", coinsPerSecond.toFloat())
        editor.putFloat("upgrade1CoinsPerSecond", upgrade1CoinsPerSecond.toFloat())
        editor.putFloat("upgrade4CoinsPerSecond", upgrade4CoinsPerSecond.toFloat())
        editor.putFloat("upgrade4CoinsPerSecond", upgrade5CoinsPerSecond.toFloat())
        editor.putFloat("upgrade4CoinsPerSecond", upgrade6CoinsPerSecond.toFloat())
        editor.putFloat("upgrade1Cost", upgrade1Cost.toFloat())
        editor.putFloat("upgrade2Cost", upgrade2Cost.toFloat())
        editor.putFloat("upgrade3Cost", upgrade3Cost.toFloat())
        editor.putFloat("upgrade4Cost", upgrade4Cost.toFloat())
        editor.putFloat("upgrade5Cost", upgrade5Cost.toFloat())
        editor.putFloat("upgrade6Cost", upgrade6Cost.toFloat())
        editor.putString("currentMinerioState", currentMinerioState)
        editor.apply()
    }

    override fun onResume() {
        super.onResume()

        val prefs = getSharedPreferences("game_prefs", Context.MODE_PRIVATE)
        coins = prefs.getFloat("coins", 0.0f).toDouble()
        coinsPerSecond = prefs.getFloat("coinsPerSecond", 0.0f).toDouble()
        upgrade1CoinsPerSecond = prefs.getFloat("upgrade1CoinsPerSecond", 2.0f).toDouble()
        upgrade4CoinsPerSecond = prefs.getFloat("upgrade4CoinsPerSecond", 100.0f).toDouble()
        upgrade5CoinsPerSecond = prefs.getFloat("upgrade5CoinsPerSecond", 25000.0f).toDouble()
        upgrade6CoinsPerSecond = prefs.getFloat("upgrade6CoinsPerSecond", 100000.0f).toDouble()
        upgrade1Cost = prefs.getFloat("upgrade1Cost", 5.0f).toDouble()
        upgrade2Cost = prefs.getFloat("upgrade2Cost", 50.0f).toDouble()
        upgrade3Cost = prefs.getFloat("upgrade3Cost", 2.0f).toDouble()
        upgrade4Cost = prefs.getFloat("upgrade4Cost", 50000.0f).toDouble()
        upgrade5Cost = prefs.getFloat("upgrade5Cost", 250000.0f).toDouble()
        upgrade6Cost = prefs.getFloat("upgrade6Cost", 1000000.0f).toDouble()
        currentMinerioState = prefs.getString("currentMinerioState", "minerio1") ?: "minerio1"

        val upgradeButton: Button = findViewById(R.id.btn_upgrade1)
        upgradeButton.text = "Upgrade Cost : ${formatDoubleNumber(upgrade1Cost)}"

        val upgradeButton2: Button = findViewById(R.id.btn_upgrade2)
        upgradeButton2.text = "Upgrade Cost : ${formatDoubleNumber(upgrade2Cost)}"

        val upgradeButton3: Button = findViewById(R.id.btn_upgrade3)
        upgradeButton3.text = "Upgrade Cost : ${formatDoubleNumber(upgrade3Cost)}"

        val upgradeButton4: Button = findViewById(R.id.btn_upgrade4)
        upgradeButton4.text = "Upgrade Cost : ${formatDoubleNumber(upgrade4Cost)}"

        val upgradeButton5: Button = findViewById(R.id.btn_upgrade5)
        upgradeButton5.text = "Upgrade Cost : ${formatDoubleNumber(upgrade5Cost)}"

        val upgradeButton6: Button = findViewById(R.id.btn_upgrade6)
        upgradeButton6.text = "Upgrade Cost : ${formatDoubleNumber(upgrade6Cost)}"

        val minerioImageView: ImageView = findViewById(R.id.minerio1)

        when (currentMinerioState) {
            "minerio1" -> minerioImageView.setImageResource(R.drawable.minerio1)
            "minerio2" -> minerioImageView.setImageResource(R.drawable.minerio2)
            "minerio3" -> minerioImageView.setImageResource(R.drawable.minerio3)
            "minerio4" -> minerioImageView.setImageResource(R.drawable.minerio4)
            "minerio5" -> minerioImageView.setImageResource(R.drawable.minerio5)
            "minerio6" -> minerioImageView.setImageResource(R.drawable.minerio6)
            "minerio7" -> minerioImageView.setImageResource(R.drawable.minerio7)
            "minerio8" -> minerioImageView.setImageResource(R.drawable.minerio8)
            "minerio9" -> minerioImageView.setImageResource(R.drawable.minerio9)
            "minerio10" -> minerioImageView.setImageResource(R.drawable.minerio10)
            "minerio11" -> minerioImageView.setImageResource(R.drawable.minerio11)
            "minerio12" -> minerioImageView.setImageResource(R.drawable.minerio12)
            "minerio13" -> minerioImageView.setImageResource(R.drawable.minerio13)
            "minerio14" -> minerioImageView.setImageResource(R.drawable.minerio14)
            "minerio15" -> minerioImageView.setImageResource(R.drawable.minerio15)
            "minerio16" -> minerioImageView.setImageResource(R.drawable.minerio16)
        }

        minerioImageView.tag = currentMinerioState

        updateUI()

        handler.removeCallbacks(updateCoinsPerSecondTask)
        handler.post(updateCoinsPerSecondTask)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putDouble("coins", coins)
        outState.putDouble("coinsPerSecond", coinsPerSecond)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        coins = savedInstanceState.getDouble("coins", coins)
        coinsPerSecond = savedInstanceState.getDouble("coinsPerSecond", coinsPerSecond)

        updateUI()

        handler.post(updateCoinsPerSecondTask)
    }

    fun goToAchivements(view: View) {
        try {
            val achievements = Intent(this@MainActivity, Activity_achivements::class.java)
            achievements.putExtra("coins", coins)
            startActivity(achievements)
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun goToRank(view: View) {
        try {
            val rank = Intent(this@MainActivity, Activity_rank::class.java)
            startActivity(rank)
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isNatal(): Boolean {
        val calendario = Calendar.getInstance()
        val formato = SimpleDateFormat("dd-MM-yyyy")
        val dataAtual = formato.format(calendario.time)

        // Verifique se a data atual é 25 de dezembro
        return dataAtual == "18-10-${getAnoAtual()}"
    }

    private fun getAnoAtual(): Int {
        val calendario = Calendar.getInstance()
        return calendario.get(Calendar.YEAR)
    }
}
