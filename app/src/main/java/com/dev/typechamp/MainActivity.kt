package com.dev.typechamp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import com.dev.typechamp.utils.DIVISIONS
import com.dev.typechamp.utils.navigation.startChallengeActivity
import com.dev.typechamp.utils.navigation.startStatsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var btnPractice: Button
    private lateinit var btnStats: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        btnPractice = findViewById(R.id.btn_practice)
        btnStats = findViewById(R.id.btn_stats)

        btnPractice.setOnClickListener {
            startChallengeActivity(DIVISIONS[0], this)
        }
        btnStats.setOnClickListener { startStatsActivity(this) }
    }
}
