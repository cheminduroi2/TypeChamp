package com.dev.typechamp.utils.chart

import android.content.Context
import com.dev.typechamp.R
import com.dev.typechamp.utils.DIVISIONS
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry


open class DivisionBarChart(private val barChart: BarChart, context: Context) {

    val barDataSets = mutableMapOf(
        *(DIVISIONS.indices.map { (DIVISIONS[it] to BarDataSet(arrayListOf(
            BarEntry(it.toFloat(), 0f)
        ), DIVISIONS[it])) }.toTypedArray())
    )
    val barData = BarData(barDataSets.values.toList())
    init {
        barDataSets[DIVISIONS[0]]?.color = context.getColor(R.color.colorPrimary)
        barDataSets[DIVISIONS[1]]?.color = context.getColor(R.color.colorMediumPurple)
        barDataSets[DIVISIONS[2]]?.color = context.getColor(R.color.colorAccent)
        barChart.data = barData
        barChart.xAxis.isEnabled = false
        barChart.axisRight.isEnabled = false
        barChart.axisLeft.axisMinimum = 0f
        barChart.axisLeft.textSize = 16f

        barChart.legend.isWordWrapEnabled = true
        barChart.legend.textSize = 16f

        barChart.setVisibleYRange(0f, 1200f, barChart.axisLeft.axisDependency)

        barChart.setDrawGridBackground(false)
        barChart.setFitBars(true)
        barChart.description = null

        barData.setValueTextSize(20f)

        barChart.setPinchZoom(false)
        barChart.extraLeftOffset = 30f

    }

    protected fun refreshChart() {
        barData.notifyDataChanged()
        barChart.notifyDataSetChanged()
        barChart.invalidate()
    }

    open fun updateData(division: String, newData: Long) {}

    open fun updateData(division: String, newData: Int) {}
}