package com.dev.typechamp.utils.navigation

import com.dev.typechamp.R
import android.content.Context
import android.content.Intent
import com.dev.typechamp.ChallengeActivity
import com.dev.typechamp.StatsActivity

fun startChallengeActivity(division: String, context: Context) {
    val intent = Intent(context, ChallengeActivity::class.java).apply {
        putExtra(context.resources.getString(R.string.division_tag), division)
    }
    context.startActivity(intent)
}

fun startStatsActivity(context: Context) {
    context.startActivity(Intent(context, StatsActivity::class.java))
}