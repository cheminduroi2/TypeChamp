package com.dev.typechamp


import android.app.AlertDialog
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.dev.typechamp.utils.VolleyCallback
import com.dev.typechamp.utils.DIVISIONS
import com.dev.typechamp.utils.navigation.startChallengeActivity
import com.dev.typechamp.utils.navigation.startStatsActivity

class ChallengeActivity : BaseActivity() {

    private lateinit var division: String

    private lateinit var challengeTextView: TextView
    private lateinit var divisionTextView: TextView
    private lateinit var timerTextView: TextView
    private lateinit var userInputEditText: EditText
    private lateinit var btnSubmit: Button

    private lateinit var timer: CountDownTimer

    companion object {
        const val CHALLENGE_DURATION = 1200000L
    }

    private var challengeText = ""
    private var currentTime = 0L
    private var highScore = CHALLENGE_DURATION

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge)
        initNavBar(R.id.activity_challenge, this)

        divisionTextView = findViewById(R.id.tv_division_title)
        challengeTextView = findViewById(R.id.tv_challenge_text)
        timerTextView = findViewById(R.id.tv_countdown_timer)
        userInputEditText = findViewById(R.id.tv_user_input)
        btnSubmit = findViewById(R.id.btn_submit)
        btnSubmit.setOnClickListener(btnClickListener)

        setDivision()
        setHeaderTextColor()
        baseViewModel.getNewChallenge(division, this, onNewChallenge)

        baseViewModel.getDivisionHighScore(division)
            .observe(this, Observer { highScore = if (it > 0L) it else highScore })
    }

    private fun setDivision() {
        division = intent.getStringExtra(resources.getString(R.string.division_tag)) ?: DIVISIONS[0]
        divisionTextView.text = division
    }

    private fun setHeaderTextColor() {
        val colors = arrayOf(R.color.colorPrimary, R.color.colorMediumPurple, R.color.colorAccent)
        when(division) {
            DIVISIONS[0] -> {
                divisionTextView.setTextColor(ContextCompat.getColor(this, colors[0]))
                timerTextView.setTextColor(ContextCompat.getColor(this, colors[0]))
            }
            DIVISIONS[1] -> {
                divisionTextView.setTextColor(ContextCompat.getColor(this, colors[1]))
                timerTextView.setTextColor(ContextCompat.getColor(this, colors[1]))
            }
            DIVISIONS[2] -> {
                divisionTextView.setTextColor(ContextCompat.getColor(this, colors[2]))
                timerTextView.setTextColor(ContextCompat.getColor(this, colors[2]))
            }
        }
    }

    private val onNewChallenge = object : VolleyCallback {
        override fun onSuccess(result: String) {
            challengeText = result
            challengeTextView.text = challengeText
            startCountdownTimer()

        }
    }

    private val btnClickListener = View.OnClickListener {
        timer.cancel()
        val userInput = userInputEditText.text.toString()
        if (userInput != challengeText) handleWrongUserInput() else handleCorrectUserInput()
    }

    private fun milliToMinAndSec(milliseconds: Long): Array<String> {
        val minutes = (milliseconds / 1000 / 60).toString()
            .padStart(2, '0')
        val seconds = (milliseconds / 1000 % 60).toString()
            .padStart(2, '0')
        return arrayOf(minutes, seconds)
    }

    private fun startCountdownTimer() {
        timer = object : CountDownTimer(CHALLENGE_DURATION, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                currentTime = CHALLENGE_DURATION - millisUntilFinished
                val (minutes, seconds) = milliToMinAndSec(millisUntilFinished)
                timerTextView.text = "$minutes:$seconds"
            }

            override fun onFinish() {
                timerTextView.text = "Time's up!"
                btnSubmit.performClick()
            }
        }
        timer.start()
    }

    private fun handleCorrectUserInput() {
        if (currentTime < highScore) {
            highScore = currentTime
            val (minutes, seconds) = milliToMinAndSec(currentTime)
            AlertDialog.Builder(this).setTitle("Congratulations!")
                .setMessage("You've set a new record for the ${division.toLowerCase()} " +
                        "division!\nNew record: $minutes minutes $seconds seconds")
                .setPositiveButton("Ok") { _, _ ->
                    baseViewModel.setDivisionHighScore(division, highScore)
                    baseViewModel.setDivisionAvgTime(division, currentTime)
                    closeChallenge(false)
                }.setCancelable(false)
                .create().show()
        } else {
            Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
            baseViewModel.setDivisionAvgTime(division, currentTime)
            closeChallenge(false)
        }
    }

    private fun handleWrongUserInput() {
        AlertDialog.Builder(this).setMessage("Your input was incorrect. Try Again?")
            .setPositiveButton("Yes") { _, _ -> closeChallenge(true) }
            .setNegativeButton("No") { _, _ -> closeChallenge(false) }
            .setCancelable(false)
            .create().show()
    }

    private fun closeChallenge(tryAgain: Boolean) {
        if (tryAgain) {
            startChallengeActivity(division, this)
        } else {
            startStatsActivity(this)
        }
        finish()
    }
}
