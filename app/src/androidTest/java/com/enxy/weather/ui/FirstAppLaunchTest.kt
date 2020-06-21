package com.enxy.weather.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.enxy.weather.R
import com.enxy.weather.data.db.AppDataBase.Companion.DATABASE_NAME
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FirstAppLaunchTest {
    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(WeatherActivity::class.java)

    companion object {
        @JvmStatic
        @BeforeClass fun clearDatabaseBeforeFirstLaunch() {
            InstrumentationRegistry.getInstrumentation().targetContext.deleteDatabase(DATABASE_NAME)
        }
    }

    @Test
    fun firstAppLaunchOpensSearchScreen() {
        onView(withId(R.id.enterHint)).check(matches(isDisplayed()))
    }
}