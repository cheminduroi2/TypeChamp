package com.dev.typechamp

import android.view.Gravity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationDrawerTest {

    @get:Rule
    var activityRule: ActivityTestRule<StatsActivity>
            = ActivityTestRule(StatsActivity::class.java)

    @Test
    fun clickOnNavItem_showsCorrectDivision() {
        onView(withId(R.id.activity_stats))
            .check(matches(isClosed(Gravity.LEFT)))
            .perform(DrawerActions.open())

        onView(withId(R.id.navigationView))
            .perform(NavigationViewActions.navigateTo(R.id.lightweight_item))

        onView(withId(R.id.tv_division_title)).check(matches(withText("Lightweight")))
    }
}