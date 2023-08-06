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