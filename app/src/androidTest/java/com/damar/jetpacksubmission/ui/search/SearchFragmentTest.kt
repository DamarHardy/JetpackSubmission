package com.damar.jetpacksubmission.ui.search

import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.damar.jetpacksubmission.R
import com.damar.jetpacksubmission.ui.MainActivity
import com.damar.jetpacksubmission.utils.EspressoIdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4ClassRunner::class)
class SearchFragmentTest{
    private lateinit var movieRV: RecyclerView
    private lateinit var tvRV: RecyclerView

    @get: Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }
    @After
    fun unregisterIdlingResource(){
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun tryingToSearchAnything(){
        onView(withId(R.id.action_search)).perform(click())
        onView(withId(R.id.search_input)).perform(typeTextSearchView("Naruto"))
        onView(withId(R.id.search_mv_rv)).check(matches(isDisplayed()))
        onView(withId(R.id.search_tv_rv)).check(matches(isDisplayed()))

        activityRule.scenario.onActivity {
            movieRV = it.findViewById(R.id.search_mv_rv)
            tvRV = it.findViewById(R.id.search_tv_rv)
        }
        assertEquals(movieRV.adapter!!.itemCount>0, true)
        assertEquals(tvRV.adapter!!.itemCount>0, true)
        pressBack()
    }

    private fun typeTextSearchView(query: String): ViewAction{
        return object : ViewAction{
            override fun getConstraints(): Matcher<View> {
                return allOf(isDisplayed(), isAssignableFrom(SearchView::class.java))
            }

            override fun getDescription(): String = "Set SearchView Text"

            override fun perform(uiController: UiController?, view: View?) {
                (view as SearchView).setQuery(query, true)
            }
        }
    }
}