package com.dev.typechamp

import android.app.Activity
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.dev.typechamp.utils.DIVISIONS
import com.dev.typechamp.utils.navigation.startChallengeActivity
import com.dev.typechamp.utils.navigation.startStatsActivity
import com.dev.typechamp.viewmodels.BaseViewModel
import com.google.android.material.navigation.NavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel

open class BaseActivity : AppCompatActivity() {

    protected val baseViewModel : BaseViewModel by viewModel()

    private lateinit var navDrawer: DrawerLayout
    private lateinit var drawerToggler: ActionBarDrawerToggle
    private lateinit var navView: NavigationView

    protected fun initNavBar(drawerLayoutID: Int, activity: Activity) {
        navDrawer = findViewById(drawerLayoutID)
        drawerToggler = ActionBarDrawerToggle(
            activity,
            navDrawer,
            R.string.open_drawer,
            R.string.close_drawer
        )
        navDrawer.addDrawerListener(drawerToggler)
        drawerToggler.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView = findViewById(R.id.navigationView)
        navView.setNavigationItemSelectedListener(navItemSelectedListener)
    }

    private val navItemSelectedListener = NavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.lightweight_item -> {
                startChallengeActivity(DIVISIONS[0], this)
                true
            }
            R.id.middleweight_item -> {
                startChallengeActivity(DIVISIONS[1], this)
                true
            }
            R.id.heavyweight_item -> {
                startChallengeActivity(DIVISIONS[2], this)
                true
            }
            R.id.stats_item -> {
                startStatsActivity(this)
                true
            }
            else -> false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (
            drawerToggler.onOptionsItemSelected(item)
        ) true else super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        navDrawer.closeDrawer(GravityCompat.START)
    }
}