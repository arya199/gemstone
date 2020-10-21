package com.arya199.gemstone

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.arya199.gemstone.converter.ConverterActivity
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ConverterActivityTest {

    @get:Rule var activityScenarioRule = activityScenarioRule<ConverterActivity>()

    private lateinit var activityScenario: ActivityScenario<ConverterActivity>

    @Before
    fun setup() {
        activityScenario = ActivityScenario.launch(ConverterActivity::class.java)
        activityScenario.onActivity {
            it.supportFragmentManager.beginTransaction()
        }
    }

    @Test
    fun testLaunch() {
        // This actually works
        /*
        activityScenario.onActivity { activity ->
            assertThat(activity.getSomething()).isEqualTo("something")
        }
        onView(withText("TextView")).check(matches(isDisplayed()))
        */
        onView(withId(R.id.currency_input_amount_text)).check(matches(isDisplayed()))
        onView(withId(R.id.currency_input_spinner)).check(matches(isDisplayed()))
        onView(withId(R.id.currency_exchange_list)).check(matches(isDisplayed()))
    }
}