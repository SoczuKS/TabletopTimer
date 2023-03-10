package soczuks.tabletoptimer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var playerNameEditTexts: Array<EditText?>
    private lateinit var playerColorButtons: Array<Button?>
    private lateinit var playerColors: Array<ColorEnvelope?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playerNameEditTexts = arrayOfNulls(8)
        playerColorButtons = arrayOfNulls(8)
        playerColors = arrayOfNulls(8)

        playerNameEditTexts[0] = findViewById(R.id.player_name1)
        playerNameEditTexts[1] = findViewById(R.id.player_name2)
        playerNameEditTexts[2] = findViewById(R.id.player_name3)
        playerNameEditTexts[3] = findViewById(R.id.player_name4)
        playerNameEditTexts[4] = findViewById(R.id.player_name5)
        playerNameEditTexts[5] = findViewById(R.id.player_name6)
        playerNameEditTexts[6] = findViewById(R.id.player_name7)
        playerNameEditTexts[7] = findViewById(R.id.player_name8)

        playerColorButtons[0] = findViewById(R.id.player_color1)
        playerColorButtons[1] = findViewById(R.id.player_color2)
        playerColorButtons[2] = findViewById(R.id.player_color3)
        playerColorButtons[3] = findViewById(R.id.player_color4)
        playerColorButtons[4] = findViewById(R.id.player_color5)
        playerColorButtons[5] = findViewById(R.id.player_color6)
        playerColorButtons[6] = findViewById(R.id.player_color7)
        playerColorButtons[7] = findViewById(R.id.player_color8)

        findViewById<Button>(R.id.player_color1).setOnClickListener {
            selectColor(0)
        }
        findViewById<Button>(R.id.player_color2).setOnClickListener {
            selectColor(1)
        }
        findViewById<Button>(R.id.player_color3).setOnClickListener {
            selectColor(2)
        }
        findViewById<Button>(R.id.player_color4).setOnClickListener {
            selectColor(3)
        }
        findViewById<Button>(R.id.player_color5).setOnClickListener {
            selectColor(4)
        }
        findViewById<Button>(R.id.player_color6).setOnClickListener {
            selectColor(5)
        }
        findViewById<Button>(R.id.player_color7).setOnClickListener {
            selectColor(6)
        }
        findViewById<Button>(R.id.player_color8).setOnClickListener {
            selectColor(7)
        }

        findViewById<Button>(R.id.startGameButton).setOnClickListener {
            val players = preparePlayerList()

            if (players.size < 2) {
                Toast.makeText(applicationContext, getString(R.string.too_few_players), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            Players = players

            val intent = Intent(this@MainActivity, TimerActivity::class.java)
            startActivity(intent)
        }
    }

    private fun preparePlayerList(): Vector<Player> {
        val players = Vector<Player>()

        for (i in 0..7) {
            val editText = playerNameEditTexts[i]

            if (editText!!.text.isEmpty() or (playerColors[i] == null)) {
                continue
            }

            val player = Player(editText.text.toString(), playerColors[i])
            players.addElement(player)
        }

        return players
    }

    private fun selectColor(index: Int) {
        ColorPickerDialog.Builder(this)
            .setTitle(getString(R.string.color))
            .setPreferenceName("ColorSelectorDialog")
            .setPositiveButton(getString(R.string.confirm),
                ColorEnvelopeListener { envelope, _ -> run {
                    playerColors[index] = envelope
                    playerColorButtons[index]!!.setBackgroundColor(envelope.color)
                    playerColorButtons[index]!!.text = ""
                } })
            .setNegativeButton(getString(R.string.cancel)) { dialogInterface, _ -> dialogInterface.dismiss() }
            .attachAlphaSlideBar(false)
            .attachBrightnessSlideBar(true)
            .setBottomSpace(12)
            .show()
    }
}