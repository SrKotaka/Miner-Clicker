package com.example.clickerproject

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.pow


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
    private var powerClick = 5.0
    private var soulscoins = 0.0
    private var soulscoinshave = 0.0
    private val hitSoundMap = mutableMapOf<Int, MediaPlayer>()
    private val handler = Handler()
    private var currentMinerioState = "minerio0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        coinsPerSecondTextView = findViewById(R.id.coins_per_second)
        upgrade1CoinsPerSecondTextView = findViewById(R.id.upgrade1_coins_per_second)
        upgrade4CoinsPerSecondTextView = findViewById(R.id.upgrade4_coins_per_second)
        upgrade5CoinsPerSecondTextView = findViewById(R.id.upgrade5_coins_per_second)
        upgrade6CoinsPerSecondTextView = findViewById(R.id.upgrade6_coins_per_second)

        val minerioImageView: ImageView = findViewById(R.id.minerio1)
        minerioImageView.tag = "minerio0"
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

        val upgradeButton2: Button = findViewById(R.id.btn_upgrade2)
        upgradeButton2.setOnClickListener {
            if (coins >= upgrade2Cost) {
                coins -= upgrade2Cost
                coinsPerSecond *= 2
                upgrade2Cost = (upgrade2Cost * 5) + 50
                upgrade1CoinsPerSecond *= 2
                upgrade4CoinsPerSecond *= 2
                upgrade5CoinsPerSecond *= 2
                upgrade6CoinsPerSecond *= 2
                powerClick *= 2
                upgradeButton2.text = "Upgrade Cost : ${formatDoubleNumber(upgrade2Cost)}"

                val minerioImages = arrayOf(
                    R.drawable.minerio2, R.drawable.minerio3, R.drawable.minerio4,
                    R.drawable.minerio5, R.drawable.minerio6, R.drawable.minerio7,
                    R.drawable.minerio8, R.drawable.minerio9, R.drawable.minerio10,
                    R.drawable.minerio11, R.drawable.minerio12, R.drawable.minerio13,
                    R.drawable.minerio14, R.drawable.minerio15, R.drawable.minerio16
                )

                val natalImages = arrayOf(
                    R.drawable.minerio2natal, R.drawable.minerio3natal, R.drawable.minerio4natal,
                    R.drawable.minerio5natal, R.drawable.minerio6natal, R.drawable.minerio7natal,
                    R.drawable.minerio8natal, R.drawable.minerio9natal, R.drawable.minerio10natal,
                    R.drawable.minerio11natal, R.drawable.minerio12natal, R.drawable.minerio13natal,
                    R.drawable.minerio14natal, R.drawable.minerio15natal, R.drawable.minerio16natal
                )

                if (minerioImages.size > 0) {
                    var nextMinerioIndex = ""
                    if(minerioImageView.tag.toString().length > 8) {
                        nextMinerioIndex = minerioImageView.tag.toString()[7]+""+minerioImageView.tag.toString()[8]
                    }else{
                        nextMinerioIndex = minerioImageView.tag.toString()[7]+""
                    }
                    val nextMinerio = Integer.parseInt(nextMinerioIndex)
                    if (nextMinerio < minerioImages.size) {
                        if(!isNatal())
                            changeImage(minerioImageView, minerioImages[nextMinerio])
                        else
                            changeImage(minerioImageView, natalImages[nextMinerio])
                        minerioImageView.tag = "minerio" + (nextMinerio + 1)
                        currentMinerioState = "minerio" + (nextMinerio + 1)
                    }
                }
                updateUI()
            }
        }  //optimize


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

    private fun createSnowfall() {
        val minerio1natal: ImageView = findViewById(R.id.minerio1)
        minerio1natal.setImageResource(R.drawable.minerio1natal) // Use as imagens de Natal
        val videoview = findViewById<View>(R.id.videoBackground) as VideoView
        val uri: Uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.backgroundnatalnew)
        videoview.setVideoURI(uri)
        videoview.start()
    }

    private fun changeImage(minerio: ImageView, imagem: Int){
        minerio.setImageResource(imagem)
    }

    private val updateCoinsPerSecondTask = object : Runnable {
        override fun run() {
            coins += coinsPerSecond
            updateUI()
            handler.postDelayed(this, 1000)
        }
    } //optimize

    private val updateAPIperMinute = object : Runnable {
        override fun run() {
            updateAPI()
            handler.postDelayed(this, 10000) //10 seconds
        }
    } //optimize

    private fun updateAPI() {
        val email = intent.getStringExtra("email")
        val url = "http://192.168.142.222:3000/usuarios/$email"

        val request = JSONObject().apply {
            put("email", email)
            put("coins", coins)
        }

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.PUT, url, request,
            { response ->
                Toast.makeText(applicationContext, "Coins updated", Toast.LENGTH_SHORT).show()
            },
            { error ->
                Toast.makeText(applicationContext, "Error to update coins", Toast.LENGTH_SHORT).show()
            }
        )

        Volley.newRequestQueue(this).add(jsonObjectRequest)
    } //optimize

    private fun updateTextViewText(viewId: Int, text: String) {
        findViewById<TextView>(viewId).text = text
    } //optimize

    private fun updateButtonState(button: Button, isEnabled: Boolean) {
        button.isEnabled = isEnabled
        button.setBackgroundColor(if (isEnabled) Color.WHITE else Color.GRAY)
    } //optimize

    private fun updateUpgradeButtons() {
        val upgradeButtonIds = listOf(
            R.id.btn_upgrade1, R.id.btn_upgrade2, R.id.btn_upgrade3,
            R.id.btn_upgrade4, R.id.btn_upgrade5, R.id.btn_upgrade6
        )

        val upgradeCosts = listOf(
            upgrade1Cost, upgrade2Cost, upgrade3Cost,
            upgrade4Cost, upgrade5Cost, upgrade6Cost
        )

        for (i in 0 until upgradeButtonIds.size) {
            val upgradeButton = findViewById<Button>(upgradeButtonIds[i])
            updateButtonState(upgradeButton, coins >= upgradeCosts[i])
        }
    } //optimize

    private fun updateResetButton() {
        val resetButton: Button = findViewById(R.id.reset_game)
        updateButtonState(resetButton, soulscoins >= 1)
    } //optimize

    private fun updateUI() {
        updateTextViewText(R.id.coins, "Coins : ${formatDoubleNumber(coins)}")
        updateTextViewText(R.id.coins_per_second, "Coins per second : ${formatDoubleNumber(coinsPerSecond)}")
        updateTextViewText(R.id.upgrade1_coins_per_second, "Coins per second : ${formatDoubleNumber(upgrade1CoinsPerSecond)}")
        updateTextViewText(R.id.upgrade4_coins_per_second, "Coins per second : ${formatDoubleNumber(upgrade4CoinsPerSecond)}")
        updateTextViewText(R.id.upgrade5_coins_per_second, "Coins per second : ${formatDoubleNumber(upgrade5CoinsPerSecond)}")
        updateTextViewText(R.id.upgrade6_coins_per_second, "Coins per second : ${formatDoubleNumber(upgrade6CoinsPerSecond)}")
        updateTextViewText(R.id.souls_coins, "Souls Coins : ${formatDoubleNumber(soulscoins)}")

        updateUpgradeButtons()
        updateResetButton()
    } //optimize

    private fun resetGame() {
        AlertDialog.Builder(this)
            .setTitle("Reset Game")
            .setMessage("Are you sure you want to reset the game? Your coins will be lost but your upgrades will increase by 10% per soul coin.")
            .setPositiveButton("Reset") { dialogInterface, _ ->
                dialogInterface.dismiss()
                performGameReset()
            }
            .setNegativeButton("Cancel") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .create()
            .show()
    } //optimize

    private fun performGameReset() {
        val bonus = soulscoinshave * 1.1

        coins = 0.0
        soulscoinshave += soulscoins
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

        // Reset UI
        updateUI()

        // Remove callbacks
        handler.removeCallbacks(updateCoinsPerSecondTask)

        // Clear preferences
        val prefs = getSharedPreferences("game_prefs", Context.MODE_PRIVATE)
        prefs.edit().clear().apply()

        // Reset minerio image
        val minerioImageView: ImageView = findViewById(R.id.minerio1)
        minerioImageView.setImageResource(R.drawable.minerio1)
        minerioImageView.tag = "minerio0"
        currentMinerioState = "minerio0"

        // Update upgrade button text
        for (i in 1..6) {
            val upgradeButton: Button = findViewById(resources.getIdentifier("btn_upgrade$i", "id", packageName))
            upgradeButton.text = "Upgrade Cost : ${formatDoubleNumber(5.0 * 10.0.pow(i - 1))}"
        }

        // Update souls coins
        val soulsCoinsHaveTextView = findViewById<TextView>(R.id.souls_coins_have)
        soulsCoinsHaveTextView.text = "Souls Coins : ${formatDoubleNumber(soulscoinshave)}"

        val soulsCoinsTextView = findViewById<TextView>(R.id.souls_coins)
        soulsCoinsTextView.text = "Souls Coins : ${formatDoubleNumber(soulscoins)}"

        // Post update task
        handler.post(updateCoinsPerSecondTask)
    } //optimize

    private fun formatDoubleNumber(number: Double): String {
        val labels = arrayOf("", "k", "M", "B", "T", "Q", "QQ", "S", "SS", "O", "N", "D", "UN", "DD")
        var formattedNumber = number
        var labelIndex = 0

        while (formattedNumber >= 1000.0 && labelIndex < labels.size - 1) {
            formattedNumber /= 1000.0
            labelIndex++
        }

        return String.format(if (formattedNumber % 1 == 0.0) "%.0f" else "%.1f", formattedNumber) + labels[labelIndex]
    } //optimize

    override fun onDestroy() {
        super.onDestroy()

        // Remove the callbacks for the updateCoinsPerSecondTask
        handler.removeCallbacks(updateCoinsPerSecondTask)

        // Release all sound resources in hitSoundMap
        hitSoundMap.values.forEach { it.release() }
    } //optimize

    override fun onPause() {
        super.onPause()

        val prefs = getSharedPreferences("game_prefs", Context.MODE_PRIVATE)
        val editor = prefs.edit()

        // Define a list of keys and their corresponding values
        val dataToSave = mapOf(
            "coins" to coins,
            "coinsPerSecond" to coinsPerSecond,
            "upgrade1CoinsPerSecond" to upgrade1CoinsPerSecond,
            "upgrade4CoinsPerSecond" to upgrade4CoinsPerSecond,
            "upgrade5CoinsPerSecond" to upgrade5CoinsPerSecond,
            "upgrade6CoinsPerSecond" to upgrade6CoinsPerSecond,
            "upgrade1Cost" to upgrade1Cost,
            "upgrade2Cost" to upgrade2Cost,
            "upgrade3Cost" to upgrade3Cost,
            "upgrade4Cost" to upgrade4Cost,
            "upgrade5Cost" to upgrade5Cost,
            "upgrade6Cost" to upgrade6Cost,
            "currentMinerioState" to currentMinerioState
        )

        // Iterate through the data and save it to preferences
        dataToSave.forEach { (key, value) ->
            when (value) {
                is Float -> editor.putFloat(key, value)
                is String -> editor.putString(key, value)
            }
        }

        editor.apply()
    } //optimize

    override fun onResume() {
        super.onResume()

        val prefs = getSharedPreferences("game_prefs", Context.MODE_PRIVATE)

        coins = prefs.getFloat("coins", 0.0f).toDouble()
        coinsPerSecond = prefs.getFloat("coinsPerSecond", 0.0f).toDouble()
        upgrade1CoinsPerSecond = prefs.getFloat("upgrade1CoinsPerSecond", 2.0f).toDouble()
        upgrade4CoinsPerSecond = prefs.getFloat("upgrade4CoinsPerSecond", 100.0f).toDouble()
        upgrade5CoinsPerSecond = prefs.getFloat("upgrade5CoinsPerSecond", 25000.0f).toDouble()
        upgrade6CoinsPerSecond = prefs.getFloat("upgrade6CoinsPerSecond", 100000.0f).toDouble()

        val upgradeCosts = mapOf(
            R.id.btn_upgrade1 to prefs.getFloat("upgrade1Cost", 5.0f).toDouble(),
            R.id.btn_upgrade2 to prefs.getFloat("upgrade2Cost", 50.0f).toDouble(),
            R.id.btn_upgrade3 to prefs.getFloat("upgrade3Cost", 2.0f).toDouble(),
            R.id.btn_upgrade4 to prefs.getFloat("upgrade4Cost", 50000.0f).toDouble(),
            R.id.btn_upgrade5 to prefs.getFloat("upgrade5Cost", 250000.0f).toDouble(),
            R.id.btn_upgrade6 to prefs.getFloat("upgrade6Cost", 1000000.0f).toDouble()
        )

        val upgradeButtonIds = listOf(R.id.btn_upgrade1, R.id.btn_upgrade2, R.id.btn_upgrade3, R.id.btn_upgrade4, R.id.btn_upgrade5, R.id.btn_upgrade6)

        for (buttonId in upgradeButtonIds) {
            val upgradeButton: Button = findViewById(buttonId)
            upgradeButton.text = "Upgrade Cost : ${formatDoubleNumber(upgradeCosts[buttonId] ?: 0.0)}"
        }

        currentMinerioState = prefs.getString("currentMinerioState", "minerio0") ?: "minerio0"

        val minerioImageView: ImageView = findViewById(R.id.minerio1)
        val minerioDrawableId = resources.getIdentifier(currentMinerioState, "drawable", packageName)
        minerioImageView.setImageResource(minerioDrawableId)
        //minerioImageView.tag = currentMinerioState

        updateUI()

        handler.removeCallbacks(updateCoinsPerSecondTask)
        handler.post(updateCoinsPerSecondTask)
    } //optimize

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val stateToSave = mapOf(
            "coins" to coins,
            "coinsPerSecond" to coinsPerSecond
        )

        stateToSave.forEach { (key, value) ->
            outState.putDouble(key, value)
        }
    } //optimize

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        coins = savedInstanceState.getDouble("coins", coins)
        coinsPerSecond = savedInstanceState.getDouble("coinsPerSecond", coinsPerSecond)

        updateUI()
        handler.post(updateCoinsPerSecondTask)
    } //optimize

    fun goToAchivements(view: View) {
        try {
            val achievements = Intent(this@MainActivity, Activity_achivements::class.java)
            achievements.putExtra("coins", coins)
            startActivity(achievements)
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT).show()
        }
    } //optimize

    fun goToRank(view: View) {
        try {
            val rank = Intent(this@MainActivity, Activity_rank::class.java)
            startActivity(rank)
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT).show()
        }
    } //optimize

    private fun isNatal(): Boolean {
        val calendario = Calendar.getInstance()
        val formato = SimpleDateFormat("dd-MM-yyyy")
        val dataAtual = formato.format(calendario.time)

        // Verifique se a data atual é 25 de dezembro
        return dataAtual == "27-10-${getAnoAtual()}"
    } //optimize

    private fun getAnoAtual(): Int {
        val calendario = Calendar.getInstance()
        return calendario.get(Calendar.YEAR)
    } //optimize

}
