package com.dev.typechamp.utils.chart

import android.content.Context
import android.util.Log
import com.github.mikephil.charting.charts.BarChart

class TimeChart(barChart: BarChart, context: Context) : DivisionBarChart(barChart, context) {

    override fun updateData(division: String, newData: Long) {
        val seconds = (newData / 1000.0).toFloat()
        barDataSets[division]?.getEntryForIndex(0)?.y = seconds
        refreshChart()
    }

}