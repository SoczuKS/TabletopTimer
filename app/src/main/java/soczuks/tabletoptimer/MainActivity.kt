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
import kotlin.collections.ArrayList


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

        playerNameEditTexts[0] = findViewById(R.id.playerName1)
        playerNameEditTexts[1] = findViewById(R.id.playerName2)
        playerNameEditTexts[2] = findViewById(R.id.playerName3)
        playerNameEditTexts[3] = findViewById(R.id.playerName4)
        playerNameEditTexts[4] = findViewById(R.id.playerName5)
        playerNameEditTexts[5] = findViewById(R.id.playerName6)
        playerNameEditTexts[6] = findViewById(R.id.playerName7)
        playerNameEditTexts[7] = findViewById(R.id.playerName8)

        playerColorButtons[0] = findViewById(R.id.playerColor1)
        playerColorButtons[1] = findViewById(R.id.playerColor2)
        playerColorButtons[2] = findViewById(R.id.playerColor3)
        playerColorButtons[3] = findViewById(R.id.playerColor4)
        playerColorButtons[4] = findViewById(R.id.playerColor5)
        playerColorButtons[5] = findViewById(R.id.playerColor6)
        playerColorButtons[6] = findViewById(R.id.playerColor7)
        playerColorButtons[7] = findViewById(R.id.playerColor8)

        findViewById<Button>(R.id.playerColor1).setOnClickListener {
            selectColor(0)
        }
        findViewById<Button>(R.id.playerColor2).setOnClickListener {
            selectColor(1)
        }
        findViewById<Button>(R.id.playerColor3).setOnClickListener {
            selectColor(2)
        }
        findViewById<Button>(R.id.playerColor4).setOnClickListener {
            selectColor(3)
        }
        findViewById<Button>(R.id.playerColor5).setOnClickListener {
            selectColor(4)
        }
        findViewById<Button>(R.id.playerColor6).setOnClickListener {
            selectColor(5)
        }
        findViewById<Button>(R.id.playerColor7).setOnClickListener {
            selectColor(6)
        }
        findViewById<Button>(R.id.playerColor8).setOnClickListener {
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