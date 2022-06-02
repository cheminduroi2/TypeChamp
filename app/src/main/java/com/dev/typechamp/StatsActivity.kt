package com.dev.typechamp

import android.os.Bundle
import androidx.lifecycle.Observer
import com.dev.typechamp.utils.chart.TimeChart
import com.dev.typechamp.utils.chart.CompletedChallengesChart
import com.dev.typechamp.utils.DIVISIONS


class StatsActivity : BaseActivity() {

    private lateinit var avgTimePerDivChart: TimeChart
    private lateinit var ccChart: CompletedChallengesChart
    private lateinit var bestTimePerDivChart: TimeChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)
        initNavBar(R.id.activity_stats, this)

        avgTimePerDivChart = TimeChart(
            findViewById(R.id.avg_time_per_division_chart),
            this
        )
        ccChart = CompletedChallengesChart(
            findViewById(R.id.total_challenge_chart),
            this
        )
        bestTimePerDivChart = TimeChart(
            findViewById(R.id.best_time_per_division_chart),
            this
        )

        for (division in DIVISIONS) {
            baseViewModel.getDivisionAvgTime(division).observe(
                this,
                Observer { avgTimePerDivChart.updateData(division, it) }
            )
            baseViewModel.getNumDivisionChallenges(division).observe(
                this,
                Observer { ccChart.updateData(division, it) }
            )
            baseViewModel.getDivisionHighScore(division).observe(
                this,
                Observer { bestTimePerDivChart.updateData(division, it) }
            )
        }


    }



}
