package com.dev.typechamp.utils.chart

import android.content.Context
import com.dev.typechamp.utils.chart.DivisionBarChart
import com.github.mikephil.charting.charts.BarChart

class CompletedChallengesChart(barChart: BarChart, context: Context) : DivisionBarChart(barChart, context) {

    override fun updateData(division: String, newData: Int) {
        barDataSets[division]?.getEntryForIndex(0)?.y = newData.toFloat()
        refreshChart()
    }
}