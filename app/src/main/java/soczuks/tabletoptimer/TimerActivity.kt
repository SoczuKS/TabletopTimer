package soczuks.tabletoptimer

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

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
            finished = true
            finish()
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
    }

    private fun start() {
        playerNameTextView.text = Players[currentPlayer].name

        timer = object : CountDownTimer(30000, 1000) {
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