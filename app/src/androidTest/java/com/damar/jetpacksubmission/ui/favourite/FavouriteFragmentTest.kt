package com.damar.jetpacksubmission.ui.favourite

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.damar.jetpacksubmission.R
import com.damar.jetpacksubmission.ui.MainActivity
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4ClassRunner::class)
class FavouriteFragmentTest{
    @get: Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp(){
        hiltRule.inject()
    }
    @Test
    fun favouriteIsAccessibleAndShowed() {
        onView(ViewMatchers.withId(R.id.action_favourite)).perform(click())
        onView(ViewMatchers.withId(R.id.fav_pager))
            .check(matches(isDisplayed()))
    }
    @Test
    fun favouriteTabIsShowed() {
        onView(ViewMatchers.withId(R.id.action_favourite)).perform(click())
        onView(ViewMatchers.withId(R.id.tab_layout))
            .check(matches(isDisplayed()))
    }
    @Test
    fun favouriteMovieAndTvIsShowed() {
        onView(ViewMatchers.withId(R.id.action_favourite)).perform(click())
        onView(ViewMatchers.withId(R.id.tab_layout)).perform(selectTabAtPosition(0))
        onView(ViewMatchers.withId(R.id.fav_movie_layout)).check(matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.tab_layout)).perform(selectTabAtPosition(1))
        onView(ViewMatchers.withId(R.id.fav_tv_layout)).check(matches(isDisplayed()))
    }

    private fun selectTabAtPosition(tabIndex: Int): ViewAction {
        return object : ViewAction {
            override fun getDescription() = "with tab at index $tabIndex"

            override fun getConstraints() =
                Matchers.allOf(isDisplayed(), ViewMatchers.isAssignableFrom(TabLayout::class.java))

            override fun perform(uiController: UiController, view: View) {
                val tabLayout = view as TabLayout
                val tabAtIndex: TabLayout.Tab = tabLayout.getTabAt(tabIndex)
                    ?: throw PerformException.Builder()
                        .withCause(Throwable("No tab at index $tabIndex"))
                        .build()

                tabAtIndex.select()
            }
        }
    }
}