package com.damar.jetpacksubmission.ui.detail

import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.damar.jetpacksubmission.R
import com.damar.jetpacksubmission.ui.MainActivity
import com.damar.jetpacksubmission.ui.home.MvFragmentTest.Companion.selectTabAtPosition
import com.damar.jetpacksubmission.ui.home.adapter.PopularMovieAdapter
import com.damar.jetpacksubmission.utils.EspressoIdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@HiltAndroidTest
@RunWith(AndroidJUnit4ClassRunner::class)
class MvDetailFragmentTest{
    private var titleMV = ""

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
    fun is_detail_movie_accessible() {
        onView(withId(R.id.tab_layout)).perform(selectTabAtPosition(MV_TAB_INDEX))
        onView(withId(R.id.mv_layout)).check(matches(isDisplayed()))

        onView(withId(R.id.mv_rv)).check(matches(isDisplayed()))
        onView(withId(R.id.mv_rv)).perform(actionOnItemAtPosition<PopularMovieAdapter.ViewHolder>(0, scrollTo()))
        onView(withId(R.id.mv_rv)).perform(actionOnItemAtPosition<PopularMovieAdapter.ViewHolder>(0, getTextRv()))
        onView(withId(R.id.mv_rv)).perform(actionOnItemAtPosition<PopularMovieAdapter.ViewHolder>(0, click()))
        onView(withId(R.id.item_title_detail)).check(matches(withText(titleMV)))
        pressBack()
    }

    @Test
    fun test_movie_detail_items() {
        onView(withId(R.id.tab_layout)).perform(selectTabAtPosition(MV_TAB_INDEX))
        onView(withId(R.id.mv_layout)).check(matches(isDisplayed()))

        onView(withId(R.id.mv_rv)).check(matches(isDisplayed()))
        onView(withId(R.id.mv_rv)).perform(actionOnItemAtPosition<PopularMovieAdapter.ViewHolder>(0, scrollTo()))
        onView(withId(R.id.mv_rv)).perform(actionOnItemAtPosition<PopularMovieAdapter.ViewHolder>(0, getTextRv()))
        onView(withId(R.id.mv_rv)).perform(actionOnItemAtPosition<PopularMovieAdapter.ViewHolder>(0, click()))

        onView(withId(R.id.movie_detail_layout)).check(matches(isCompletelyDisplayed()))
        onView(withId(R.id.pager_detail_mv)).perform(swipeLeft())

        onView(withId(R.id.item_title_detail)).perform(scrollTo())
        onView(withId(R.id.item_title_detail)).check(matches(withText(titleMV)))

        onView(withId(R.id.item_desc_detail)).perform(scrollTo())
        onView(withId(R.id.item_desc_detail)).check(matches(isDisplayed()))

        onView(withId(R.id.item_release_detail)).perform(scrollTo())
        onView(withId(R.id.item_release_detail)).check(matches(isDisplayed()))

        onView(withId(R.id.item_poster_detail)).perform(scrollTo())
        onView(withId(R.id.item_poster_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.horizontal_genre_sv)).perform(swipeLeft())

        onView(withId(R.id.item_desc_full_detail)).perform(scrollTo())
        onView(withId(R.id.item_desc_full_detail)).check(matches(isDisplayed()))

        onView(withId(R.id.images_detail_rv)).perform(scrollTo())
        onView(withId(R.id.images_detail_rv)).perform(swipeLeft())

    }

    private fun getTextRv(): ViewAction{
        return object : ViewAction{
            override fun getConstraints(): Matcher<View> {
                return isDisplayed()
            }

            override fun getDescription(): String {
                return "Get Text From View Holder"
            }

            override fun perform(uiController: UiController?, view: View?) {
                val textView = view?.findViewById(R.id.item_title_rv) as TextView
                titleMV = textView.text.toString()
            }

        }
    }
    fun slowSwipeUp(): ViewAction{
        val start: CoordinatesProvider = GeneralLocation.VISIBLE_CENTER
        val end: CoordinatesProvider = GeneralLocation.BOTTOM_CENTER
        return actionWithAssertions(GeneralSwipeAction(
                Swipe.FAST,
                end,start,
                Press.FINGER
        ))
    }
    companion object {
        private const val MV_TAB_INDEX = 0
    }
}