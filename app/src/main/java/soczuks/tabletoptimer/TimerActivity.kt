package soczuks.tabletoptimer

import android.app.Dialog
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.slider.Slider

class TimerActivity : AppCompatActivity() {
    private lateinit var playerNameTextView: TextView
    private lateinit var timerTextView: TextView
    private lateinit var mainLayout: ConstraintLayout
    private lateinit var clickableLayout: ConstraintLayout
    private lateinit var finishButton: Button

    private lateinit var timer: CountDownTimer
    private var mediaPlayer : MediaPlayer? = null

    private var currentPlayer = 0
    private var running = false
    private var finished = false
    private var millisecondsPerTurn : Long = 30000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        findViews()
        setClickListeners()
        initMediaPlayer()

        prepareGame()
    }

    override fun onStop() {
        super.onStop()

        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun setClickListeners() {
        finishButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage(getString(R.string.are_you_sure))
                .setCancelable(false)
                .setPositiveButton(
                    getString(R.string.yes)
                ) { _, _ -> this@TimerActivity.finish() }
                .setNegativeButton(
                    getString(R.string.no)
                ) { dialog, _ -> dialog.cancel() }
                .show()
        }
        clickableLayout.setOnClickListener {
            if (!running and !finished) {
                start()
            } else if (!finished) {
                nextPlayer()
            }
        }
    }

    private fun findViews() {
        mainLayout = findViewById(R.id.mainLayout)
        playerNameTextView = findViewById(R.id.playerName)
        timerTextView = findViewById(R.id.timer)
        finishButton = findViewById(R.id.finishButton)
        clickableLayout = findViewById(R.id.clickableLayout)
    }

    private fun initMediaPlayer() {
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm)
        mediaPlayer?.isLooping = false
    }

    private fun prepareGame() {
        mainLayout.setBackgroundColor(Players[currentPlayer].color!!.color)
        playerNameTextView.text = Players[currentPlayer].name

        val timeSelectorDialog = Dialog(this)
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = inflater.inflate(R.layout.time_selector, findViewById(R.id.time_selector_root))
        timeSelectorDialog.setContentView(layout)

        val button = layout.findViewById<Button>(R.id.time_confirm)
        val slider = layout.findViewById<Slider>(R.id.time_selector)
        val timeDisplay = layout.findViewById<TextView>(R.id.time_display)
        timeDisplay.text = (slider.value).toInt().toString()

        val sliderListener: Slider.OnSliderTouchListener = object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
                timeDisplay.text = (slider.value).toInt().toString()
            }

            override fun onStopTrackingTouch(slider: Slider) {
                timeDisplay.text = (slider.value).toInt().toString()
            }
        }
        slider.addOnSliderTouchListener(sliderListener)

        button.setOnClickListener {
            millisecondsPerTurn = (slider.value * 1000).toLong()
            timeSelectorDialog.dismiss()
        }

        timeSelectorDialog.show()
    }

    private fun start() {
        playerNameTextView.text = Players[currentPlayer].name

        timer = object : CountDownTimer(millisecondsPerTurn, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var seconds = millisUntilFinished / 1000
                val minutes = (seconds / 60).toInt()
                seconds -= minutes * 60
                timerTextView.text = getString(R.string.time_remaining, minutes, seconds)
            }

            override fun onFinish() {
                mediaPlayer?.start()
            }
        }.start()

        running = true
    }

    private fun nextPlayer() {
        if (currentPlayer == Players.size - 1) {
            currentPlayer = 0
        } else {
            ++currentPlayer
        }

        playerNameTextView.text = Players[currentPlayer].name
        mainLayout.setBackgroundColor(Players[currentPlayer].color!!.color)
        timer.start()
    }
}