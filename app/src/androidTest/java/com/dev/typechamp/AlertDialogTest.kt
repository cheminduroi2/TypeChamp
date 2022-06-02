package com.dev.typechamp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlertDialogTest {

    @get:Rule
    var activityRule: ActivityTestRule<ChallengeActivity>
            = ActivityTestRule(ChallengeActivity::class.java)

    @Test
    fun alertDialog_showWrongInputDialog() {
        onView(withId(R.id.btn_submit))
            .perform(click())

        onView(withText("Your input was incorrect. Try Again?"))
            .check(matches(isDisplayed()))
    }
}