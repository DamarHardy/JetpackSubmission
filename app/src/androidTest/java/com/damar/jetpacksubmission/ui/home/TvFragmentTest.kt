package com.damar.jetpacksubmission.ui.home

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.*
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.damar.jetpacksubmission.R
import com.damar.jetpacksubmission.ui.MainActivity
import com.damar.jetpacksubmission.ui.home.adapter.PopularMovieAdapter
import com.damar.jetpacksubmission.utils.EspressoIdlingResource
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@HiltAndroidTest
@RunWith(AndroidJUnit4ClassRunner::class)
class TvFragmentTest {
    private var titleTV = ""
    private lateinit var recyclerView: RecyclerView
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
    fun is_pagerVisible() {
        onView(withId(R.id.tab_layout)).perform(selectTabAtPosition(1))
        onView(withId(R.id.tv_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_pager)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_pager)).perform(swipeLeft())
    }

    @Test
    fun scroll_to_PagerLastItem_thenNavigate() {
        onView(withId(R.id.tab_layout)).perform(selectTabAtPosition(1))
        onView(withId(R.id.tv_layout)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_pager)).check(matches(isDisplayed()))
        for(i in 0..18){
            onView(withId(R.id.tv_pager)).perform(swipeLeft())
        }
        runBlocking {
            delay(1_000)
        }
        val text = getText(onView(allOf(withId(R.id.item_title), isDisplayed())))
        onView(withId(R.id.tv_pager)).perform(click())
        onView(withId(R.id.item_title_detail)).check(matches(withText(text)))
        pressBack()
    }

    @Test
    fun is_recyclerViewVisible() {
        onView(withId(R.id.tab_layout)).perform(selectTabAtPosition(1))
        onView(withId(R.id.tv_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_rv)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_rv)).perform(scrollToPosition<PopularMovieAdapter.ViewHolder>(19))
    }

    @Test
    fun scroll_to_RVLastItem_thenNavigate() {
        onView(withId(R.id.tab_layout)).perform(selectTabAtPosition(1))
        onView(withId(R.id.tv_layout)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_rv)).check(matches(isDisplayed()))
        //--Get Recycler View Size
        activityRule.scenario.onActivity {
            recyclerView = it.findViewById(R.id.mv_rv) as RecyclerView
        }
        val rvSize = recyclerView.adapter?.itemCount ?: 0
        if(rvSize > 0){
            onView(withId(R.id.tv_rv)).perform(actionOnItemAtPosition<PopularMovieAdapter.ViewHolder>(
                    19,
                    ViewActions.scrollTo()
            )
            )
            onView(withId(R.id.tv_rv)).perform(
                    actionOnItemAtPosition<PopularMovieAdapter.ViewHolder>(
                            19,
                            getTextRv()
                    )
            )
            onView(withId(R.id.tv_rv)).perform(
                    actionOnItemAtPosition<PopularMovieAdapter.ViewHolder>(
                            19,
                            click()
                    )
            )
            onView(withId(R.id.item_title_detail))
                    .check(matches(withText(titleTV)))
            pressBack()
        }
    }

    private fun selectTabAtPosition(tabIndex: Int): ViewAction {
        return object : ViewAction {
            override fun getDescription() = "with tab at index $tabIndex"

            override fun getConstraints() = allOf(
                isDisplayed(),
                isAssignableFrom(TabLayout::class.java)
            )

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
    private fun getTextRv(): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isDisplayed()
            }

            override fun getDescription(): String {
                return "Get Text From View Holder"
            }

            override fun perform(uiController: UiController?, view: View?) {
                val textView = view?.findViewById(R.id.item_title_rv) as TextView
                titleTV = textView.text.toString()
            }
        }
    }
    private fun getText(viewInteraction: ViewInteraction): String{
        var string = ""
        viewInteraction.perform(object: ViewAction{
            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(TextView::class.java)
            }

            override fun getDescription(): String {
                return "getting text from a TextView"
            }

            override fun perform(uiController: UiController?, view: View?) {
                val textView = view as TextView
                string = textView.text.toString()
            }
        })
        return string
    }
}